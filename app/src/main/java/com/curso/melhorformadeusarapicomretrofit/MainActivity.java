package com.curso.melhorformadeusarapicomretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.curso.melhorformadeusarapicomretrofit.api.CepService;
import com.curso.melhorformadeusarapicomretrofit.api.DataService;
import com.curso.melhorformadeusarapicomretrofit.model.CEP;
import com.curso.melhorformadeusarapicomretrofit.model.Foto;
import com.curso.melhorformadeusarapicomretrofit.model.Postagem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button btnRecuperar;
    private TextView txtRes;
    private Retrofit retrofit;//biblioteca para usar APIs mais facil
    private List<Foto> listFoto = new ArrayList<>();//e usada para recuperar a lista da dados da API

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRecuperar = findViewById(R.id.btnRecuperar);
        txtRes = findViewById(R.id.txtResultado);


        //Essa aqui cria uma retroft para lista varios dados.
        /*retrofit = new Retrofit
                .Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/photos/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
*/
        //Essa aqui cria uma retroft normal.
        retrofit = new Retrofit
                .Builder()//cria a retrofit
                .baseUrl("https://viacep.com.br/ws/")//pega o link da api
                .addConverterFactory(GsonConverterFactory.create())//escolhendo qual ira convert os dados q vem da API
                .build();//retrofit criada


        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //recuperarCepRetroft();
                //recuperarListReftroft();
                //salvarPostagem();
                //atualizarPostagem();
                removerPostagem();
            }
        });
    }

    private void removerPostagem() {
        DataService service = retrofit.create(DataService.class);
        Call<Void> call = service.removerPostagem(2);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()){
                    txtRes.setText("Status: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void atualizarPostagem() {
        Postagem postagem = new Postagem("123",null,"Corpo postagem");
        DataService service = retrofit.create(DataService.class);
        Call<Postagem> call = service.autalizarPostagem(2,postagem);

        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                Postagem postResume = response.body();
                txtRes.setText("Codigo: "+response.code()+", id: "+postResume.getId()+
                        ", userId: "+postResume.getUserId()+
                        ", Titulo: "+postResume.getTitle()+
                        ", body: "+postResume.getBody());

            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });
    }

    private void salvarPostagem(){
        //salvando dados

        //Salvando os dados na class
        DataService service = retrofit.create(DataService.class);
        Call<Postagem> call = service.salvarPostagem("123","Titulo da postagem","Corpo postagem");

        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if (response.isSuccessful()){
                    //toda vez que salvar dados no servidor ele ira mandar uma resposta, ent por isso fazer esse passo a baixo
                    Postagem postagemResposta = response.body();
                    txtRes.setText("Codigo: "+response.code()+", id: "+postagemResposta.getId()+", Titulo: "+postagemResposta.getTitle());

                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });
    }

    //esse metodo recupera uma lista de dados da API
    private void recuperarListReftroft(){
        DataService service = retrofit.create(DataService.class);
        Call<List<Foto>> call = service.recuperaFoto();

        call.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                if (response.isSuccessful()){
                    listFoto = response.body();
                    //pronto dessa forma se recupera uma lista de dados da API, e so usar um recyclerView usar o adapter e passar essa
                    //lista no construtor do adapter ai esta tudo certo.
                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {

            }
        });

    }

    //esse metodo recupera os dados da API normal 1 por 1
    private void recuperarCepRetroft(){
        CepService cepService = retrofit.create(CepService.class);
        Call<CEP> call = cepService.recuperarCep("18079728");//passando o cep digitado pelo usuario
        //aqui ele recupera o metodo da class, na class ja esta sendo passado os dados


        //aqui ele cria automaticamente uma class assycrona para baixar os dados sem usas a thread principal
        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                //caso tudo de certo em recuperar os dados, ele ira responder aqui
                if (response.isSuccessful()){
                    CEP cep = response.body();// aqui ele recupera todos os dados vindo da api, ja prontinho pra usar
                    txtRes.setText(cep.getLogradouro());//mostrando o dado recuperado
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
                //caso de algo errado ele avisa nesse metodo
            }
        });



    }
}