package com.jewelry.api.product.service;

import com.jewelry.api.commondata.GenericServiceImpl;
import com.jewelry.api.commondata.SearchSpecification;
import com.jewelry.api.commondata.Slug;
import com.jewelry.api.commondata.model.PagedResult;
import com.jewelry.api.commondata.model.SearchCriteria;
import com.jewelry.api.gdrive.manager.itf.GDriveFileManager;
import com.jewelry.api.gdrive.manager.itf.GDriveFolderManager;
import com.jewelry.api.product.dto.product.*;
import com.jewelry.api.product.entity.Image;
import com.jewelry.api.product.entity.Product;
import com.jewelry.api.product.projection.product.*;
import com.jewelry.api.product.repository.*;
import com.jewelry.api.product.service.itf.ProductService;
import com.jewelry.api.util.MapDtoToModel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ProductServiceImpl extends GenericServiceImpl<Product, Long> implements ProductService {

    private ProductRepository productRepository;
    private SupplierRepository supplierRepository;
    private CategoryRepository categoryRepository;
    private GoldTypeRepository goldTypeRepository;
    private ImageRepository imageRepository;

    private GDriveFolderManager gDriveFolderManager;
    private GDriveFileManager gDriveFileManager;
    private Environment env;
    private MapDtoToModel<Object, Product> mapper;
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public Product save(ProductCreateDto dto) {
        Product newProduct = new Product();

        newProduct = mapper.map(dto, newProduct);

        newProduct.setSlug(new Slug().slugify(newProduct.getName() + " " + newProduct.getSku()));

        newProduct.setSupplier(supplierRepository.findByCode(dto.getSupplierCode()).get());

        newProduct.setCategory(categoryRepository.findByCode(dto.getCategoryCode()).get());

        newProduct.setGoldType(goldTypeRepository.findByPercentage(dto.getGoldType()).get());

        String folderId = gDriveFolderManager
                .create(env.getProperty("jewelry.gdrive.folder.product"), dto.getSku());

        if(dto.getAvatar() != null) {
            String avatar = gDriveFileManager.uploadFile(folderId, Collections.singletonList(dto.getAvatar())).get(0);
            newProduct.setAvatar(avatar);
        }

        if(dto.getImages() != null) {
            List<String> imageIds = gDriveFileManager.uploadFile(folderId, dto.getImages());
            for(String imageId : imageIds) {
                newProduct.addImage(new Image(imageId));
            }
        }

        return productRepository.save(newProduct);
    }

    @SneakyThrows
    @Override
    @Transactional
    public Product updateProductInfo(ProductUpdateDto dto, Long id) {
        Product productUpdate = productRepository.getOne(id);

        productUpdate = mapper.map(dto, productUpdate);

        productUpdate.setSlug(new Slug().slugify(productUpdate.getName() + " " + productUpdate.getSku()));

        productUpdate.setSupplier(supplierRepository.findByCode(dto.getSupplierCode()).get());

        productUpdate.setCategory(categoryRepository.findByCode(dto.getCategoryCode()).get());

        productUpdate.setGoldType(goldTypeRepository.findByPercentage(dto.getGoldType()).get());

        if(dto.getAvatar() != null) {
            gDriveFileManager.deleteFile(Collections.singletonList(productUpdate.getAvatar()));
            String folderId = gDriveFolderManager
                    .findIdByName(env.getProperty("jewelry.gdrive.folder.product"), dto.getSku());
            String avatar = gDriveFileManager.uploadFile(folderId, Collections.singletonList(dto.getAvatar())).get(0);
            productUpdate.setAvatar(avatar);
        }

        if(dto.getImagesRemoved() != null) {
            gDriveFileManager.deleteFile(dto.getImagesRemoved());
            Set<Image> images = imageRepository.findByGDriveIdIn(dto.getImagesRemoved());
            for(Image image : images) {
                productUpdate.removeImage(image);
            }
        }

        if(dto.getNewImages() != null) {
            String folderId = gDriveFolderManager
                    .findIdByName(env.getProperty("jewelry.gdrive.folder.product"), dto.getSku());
            List<String> imageIds = gDriveFileManager.uploadFile(folderId, dto.getNewImages());
            for(String imageId : imageIds) {
                productUpdate.addImage(new Image(imageId));
            }
        }

        return productRepository.save(productUpdate);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Product product = productRepository.getOne(id);
        String folderId = gDriveFolderManager
                .findIdByName(env.getProperty("jewelry.gdrive.folder.product"), product.getSku());
        gDriveFolderManager.deleteFolder(folderId);
        super.deleteById(id);
    }

    /* USER */
    @Override
    public PagedResult<ShortProductDto> findShortProducts(Pageable pageable) {
        Page<ShortProductProjection> shortProductsPaged
                = productRepository.findShortProductsBy(pageable, ShortProductProjection.class);

        modelMapper.typeMap(ShortProductProjection.class, ShortProductDto.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> (Integer)ctx.getSource() > 0)
                            .map(ShortProductProjection::getQuantity, ShortProductDto::setInStock);
                });
        return new PagedResult<>(
                shortProductsPaged.getContent().stream()
                        .map(p -> modelMapper.map(p, ShortProductDto.class)).collect(Collectors.toList()),
                shortProductsPaged.getTotalElements(),
                shortProductsPaged.getTotalPages(),
                shortProductsPaged.getNumber() + 1
        );
    }

    @Override
    public PagedResult<ShortProductDto> findShortProductsWithSearch(SearchCriteria searchCriteria, Pageable pageable) {
        SearchSpecification<Product> productSearchSpecification = new SearchSpecification<>(searchCriteria);
        Page<Product> productsPaged = productRepository.findAll(productSearchSpecification, pageable);

        modelMapper.typeMap(Product.class, ShortProductDto.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> (Integer)ctx.getSource() > 0)
                            .map(Product::getQuantity, ShortProductDto::setInStock);
                });
        return new PagedResult<>(
                productsPaged.getContent().stream()
                        .map(p -> modelMapper.map(p, ShortProductDto.class)).collect(Collectors.toList()),
                productsPaged.getTotalElements(),
                productsPaged.getTotalPages(),
                productsPaged.getNumber() + 1
        );
    }

    @Override
    public PagedResult<ShortProductDto> findShortProductsByCategory(String categorySlug, Pageable pageable) {
        Page<ShortProductProjection> shortProductsPaged
                = productRepository.findByCategorySlug(categorySlug, pageable, ShortProductProjection.class);

        modelMapper.typeMap(ShortProductProjection.class, ShortProductDto.class)
                .addMappings(mapper -> {
                    mapper.using(ctx -> (Integer)ctx.getSource() > 0)
                            .map(ShortProductProjection::getQuantity, ShortProductDto::setInStock);
                });
        return new PagedResult<>(
                shortProductsPaged.getContent().stream()
                        .map(p -> modelMapper.map(p, ShortProductDto.class)).collect(Collectors.toList()),
                shortProductsPaged.getTotalElements(),
                shortProductsPaged.getTotalPages(),
                shortProductsPaged.getNumber() + 1
        );
    }

    @Override
    public ProductDetailsDto findProductDetailsBySlug(String slug) {
        Optional<ProductDetailsProjection> productDetailsProjection
                = productRepository.findBySlug(slug, ProductDetailsProjection.class);
        if(productDetailsProjection.isPresent()) {
            Set<String> images = imageRepository.findGDriveIdByProductSku(productDetailsProjection.get().getSku());
            modelMapper.typeMap(ProductDetailsProjection.class, ProductDetailsDto.class)
                    .addMappings(mapper -> {
                        mapper.using(ctx -> (Integer)ctx.getSource() > 0)
                                .map(ProductDetailsProjection::getQuantity, ProductDetailsDto::setInStock);
                        mapper.skip(ProductDetailsDto::setImages);
                    });
            ProductDetailsDto productDetailsDto = modelMapper.map(productDetailsProjection.get(), ProductDetailsDto.class);
            productDetailsDto.setImages(images);
            return productDetailsDto;
        }
        return null;
    }

    /* ADMIN */
    @Override
    public PagedResult<ProductSummaryDto> findProductsSummary(Pageable pageable) {
        Page<ProductSummaryProjection> productsSummaryProjectionPaged
                = productRepository.findProductsSummaryBy(pageable, ProductSummaryProjection.class);

        return new PagedResult<>(
                productsSummaryProjectionPaged.getContent().stream()
                        .map(p -> modelMapper.map(p, ProductSummaryDto.class)).collect(Collectors.toList()),
                productsSummaryProjectionPaged.getTotalElements(),
                productsSummaryProjectionPaged.getTotalPages(),
                productsSummaryProjectionPaged.getNumber() + 1
        );
    }

    @Override
    public PagedResult<ProductSummaryDto> findProductsSummaryWithSearch(SearchCriteria searchCriteria, Pageable pageable) {
        SearchSpecification<Product> productSearchSpecification = new SearchSpecification<>(searchCriteria);
        Page<Product> productsPaged = productRepository.findAll(productSearchSpecification, pageable);

        return new PagedResult<>(
                productsPaged.getContent().stream()
                        .map(p -> modelMapper.map(p, ProductSummaryDto.class)).collect(Collectors.toList()),
                productsPaged.getTotalElements(),
                productsPaged.getTotalPages(),
                productsPaged.getNumber() + 1
        );
    }

    @Override
    public ProductDetailsAdminDto findProductDetailsAdminById(Long id) {
        Optional<ProductDetailsAdminProjection> productDetailsAdminProjection = productRepository
                .findById(id, ProductDetailsAdminProjection.class);

        if(productDetailsAdminProjection.isPresent()) {
            Set<String> images = imageRepository.findGDriveIdByProductSku(productDetailsAdminProjection.get().getSku());
            ProductDetailsAdminDto productDetailsAdminDto
                    = modelMapper.map(productDetailsAdminProjection.get(), ProductDetailsAdminDto.class);
            productDetailsAdminDto.setImages(images);
            return productDetailsAdminDto;
        }
        return null;
    }
}
