package com.curso.melhorformadeusarapicomretrofit.api;

import com.curso.melhorformadeusarapicomretrofit.model.CEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepService {
    //usada para recuperar dados da api normal 1 por 1
//aqui nessa interfase e possivel ter varios metodo q trab com os pedacos do link da api


    @GET("{cep}/json/")//tem q utulizar chaves para poder passar dados recuperado da interface, ex: {cep} ele pega o dado digitado pelo cliente
    Call<CEP> recuperarCep(@Path("cep") String cep);//tem que fazer dessa msm forma para recuperar o dado digitado e buscar na API
    //aqui fica o metodo q ira buscar os dados em ordem q o model esta

}
