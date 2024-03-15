package com.template.marketplace_vk.di

import com.template.marketplace_vk.data.repositoryimpl.ProductsRepositoryImpl
import com.template.marketplace_vk.domain.repository.ProductsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductsRepositoryModule {
    @Binds
    abstract fun bindProductsRepositoryToProductsRepositoryImpl(impl: ProductsRepositoryImpl):
            ProductsRepository
}