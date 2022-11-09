package com.appdetex.harvest.api;

import com.appdetex.harvest.marketplace.AbstractHarvester;
import com.appdetex.harvest.marketplace.MercadoLivreBrHarvester;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException, HarvestException {
        MercadoLivreBrHarvester mercadoLivreBrHarvester = new MercadoLivreBrHarvester();
        mercadoLivreBrHarvester.parseTarget("jacuzzi", 10);


    }
}
