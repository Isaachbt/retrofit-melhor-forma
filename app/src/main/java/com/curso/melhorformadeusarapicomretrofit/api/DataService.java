package com.curso.melhorformadeusarapicomretrofit.api;

import com.curso.melhorformadeusarapicomretrofit.model.Foto;
import com.curso.melhorformadeusarapicomretrofit.model.Postagem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DataService {
    //usada para recuperar uma lista de dados
    //essa interface esta recuperando dados diferentes, uma api tem varios dados tipo foto,post e nomes, ai e so passar o link da
    //api e nos get e so passar o final do link q mostra oq esta sendo recuperado
    //quando usa Call<Postagem> significa que tem retorno e quando usa Call<void> nao tem retorno

    @GET("/photos")
    Call<List<Foto>> recuperaFoto();

    @GET("/posts")
    Call<List<Postagem>> recuperaPostagem();

    @POST("/posts")//esse e usado para salvar dados no servidor
    Call<Postagem> salvarPostagem(@Body Postagem postagem);//dessa forma ele salva os dados usando o corpo da class
    //@Body quer dizer corpo, ent ele pega o corpo da class e salva da forma que esta


    @FormUrlEncoded
    @POST("/photos")//Essa e uma forma bem mas simples de salvar os dados, ele pega os dados e salva da forma que esta ai
    Call<Postagem> salvarPostagem(
            @Field("userId") String userId,
            @Field("title") String title,
            @Field("body") String body
    );

    @PUT("/photos/{id}")//atualizando os dados no sevidor/api
    Call<Postagem> autalizarPostagem(@Path("id") int id, @Body Postagem postagem);

    @PATCH("/photos/{id}")//esse tem a msm função do @PUT, a unica diferenca e que esse ele apenas salva oq foi selecionado,
        // por ex: se eu deixar o titulo como null ele ira deixar o titulo anterior sem sobrescrever nem nada, agora o @PUT ele atualiza,
    //tudo, ex: se eu deixar o titulo como null o @PUT ira salvar como null e ira mostrar pro usuario q o titulo esta null
    Call<Postagem> autalizarPostagemPath(@Path("id") int id, @Body Postagem postagem);

    @DELETE("/photos/{id}")
    Call<Void> removerPostagem(@Path("id") int id);
}
