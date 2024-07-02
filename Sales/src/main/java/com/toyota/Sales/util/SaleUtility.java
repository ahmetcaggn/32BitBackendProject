package com.toyota.Sales.util;

import com.toyota.entity.Sale;
import com.toyota.entity.SaleProduct;

public final class SaleUtility {

    public static void setSaleTotalAmount(Sale sale) {
        float total = 0f;
        for (SaleProduct saleProduct : sale.getSalesProducts()) {
            total += saleProduct.getQuantity() * saleProduct.getProduct().getPrice();
        }
        sale.setTotalAmount(total);
    }

    public static void setSaleTotalAmount(Sale sale, SaleProduct sP) {
        float total = 0f;
        for (SaleProduct saleProduct : sale.getSalesProducts()) {
            total += saleProduct.getQuantity() * saleProduct.getProduct().getPrice();
        }
        total += sP.getQuantity() * sP.getProduct().getPrice();
        sale.setTotalAmount(total);
    }


    public static void setSaleTotalTax(Sale sale) {
        float total = 0f;
        for (SaleProduct saleProduct : sale.getSalesProducts()) {
            total += saleProduct.getQuantity() * saleProduct.getProduct().getPrice() * saleProduct.getProduct().getTax() / 100;
        }
        sale.setTotalTax(total);
    }

    public static void setSaleTotalTax(Sale sale, SaleProduct sP) {
        float total = 0f;
        for (SaleProduct saleProduct : sale.getSalesProducts()) {
            total += saleProduct.getQuantity() * saleProduct.getProduct().getPrice() * saleProduct.getProduct().getTax() / 100;
        }
        total += sP.getQuantity() * sP.getProduct().getPrice() * sP.getProduct().getTax() / 100;
        sale.setTotalTax(total);
    }
}
