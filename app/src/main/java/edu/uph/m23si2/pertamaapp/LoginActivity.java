package edu.uph.m23si2.pertamaapp;

import static android.text.method.TextKeyListener.clear;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import edu.uph.m23si2.pertamaapp.API.ApiResponse;
import edu.uph.m23si2.pertamaapp.API.ApiResponseKota;
import edu.uph.m23si2.pertamaapp.API.ApiService;
import edu.uph.m23si2.pertamaapp.model.KRS;
import edu.uph.m23si2.pertamaapp.model.KRSDetail;
import edu.uph.m23si2.pertamaapp.model.KelasMataKuliah;
import edu.uph.m23si2.pertamaapp.model.Kota;
import edu.uph.m23si2.pertamaapp.model.Mahasiswa;
import edu.uph.m23si2.pertamaapp.model.Matakuliah;
import edu.uph.m23si2.pertamaapp.model.Prodi;
import edu.uph.m23si2.pertamaapp.model.Provinsi;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtNama, edtPassword;
    Spinner sprProvinsi, sprKota;

    List<Provinsi> provinsiList = new ArrayList<>();
    List<Kota> kotaList = new ArrayList<>();
    List<String> namaProvinsi = new ArrayList<>();
    List<String> namaKota = new ArrayList<>();
    ArrayAdapter<String> provinsiAdapter;
    ArrayAdapter<String> kotaAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("default.realm")
                .schemaVersion(1)
                .allowWritesOnUiThread(true) // sementara aktifkan untuk demo
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        btnLogin = findViewById(R.id.btnLogin);
        edtNama = findViewById(R.id.edtNama);
        edtPassword = findViewById(R.id.edtPassword);

        initData();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDashboard();
            }
        });
        sprProvinsi = findViewById(R.id.sprProvinsi);
        sprKota = findViewById(R.id.sprKota);
        provinsiAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,namaProvinsi);
        provinsiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sprProvinsi.setAdapter(provinsiAdapter);

        kotaAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,namaKota);
        kotaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sprKota.setAdapter(kotaAdapter);



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://wilayah.id")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        //panggil API
        apiService.getProvinsi().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.body()!=null){
                    provinsiList = response.body().getData();
                    namaProvinsi.clear();
                    for(Provinsi p: provinsiList){
                        if(p.getName()!=null){
                            Log.d("Provinsi", p.getName());
                            namaProvinsi.add(p.getName());
                        }
                    }

                    provinsiAdapter.notifyDataSetChanged();

                    sprProvinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Provinsi selected = provinsiList.get(position);
                            Log.d("Provinsi", selected.getCode() + " - " + selected.getName());

                            apiService.getKota(selected.getCode()).enqueue(new Callback<ApiResponseKota>() {
                                @Override
                                public void onResponse(Call<ApiResponseKota> call, Response<ApiResponseKota> response) {
                                    if (response.isSuccessful() && response.body() != null) {
                                        kotaList = response.body().getData();
                                        namaKota.clear();
                                        for (Kota k : kotaList) {
                                            namaKota.add(k.getName());
                                        }

                                        // Update adapter kota
                                        kotaAdapter.notifyDataSetChanged();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ApiResponseKota> call, Throwable t) {
                                    Log.e("API Kota", "Gagal ambil kota", t);
                                }
                            });
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"Gagal :"+t.getMessage(),Toast.LENGTH_LONG);
            }
        });


    }
    public void initData() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(r -> {

            Prodi prodiSI = r.where(Prodi.class).equalTo("prodiID", 0).findFirst();
            if (prodiSI == null) {
                prodiSI = r.createObject(Prodi.class, 0);
                prodiSI.setFakultas("Fakultas Teknologi Informasi");
                prodiSI.setNama("Sistem Informasi");
            }


            Matakuliah matMobile = r.where(Matakuliah.class).equalTo("matakuliahID", 0).findFirst();
            if (matMobile == null) {
                matMobile = r.createObject(Matakuliah.class, 0);
                matMobile.setNama("Pemrograman Mobile Lanjut");
                matMobile.setSks(3);
                matMobile.setProdi(prodiSI);
            }

            Matakuliah matPBO = r.where(Matakuliah.class).equalTo("matakuliahID", 1).findFirst();
            if (matPBO == null) {
                matPBO = r.createObject(Matakuliah.class, 1);
                matPBO.setNama("Pemrograman Berorientasi Objek");
                matPBO.setSks(3);
                matPBO.setProdi(prodiSI);
            }


            KelasMataKuliah PM23SI2 = r.where(KelasMataKuliah.class).equalTo("kelasmatakuliahID", 0).findFirst();
            if (PM23SI2 == null) {
                PM23SI2 = r.createObject(KelasMataKuliah.class, 0);
                PM23SI2.setKelas("23SI2");
                PM23SI2.setDosen("Sir Ade");
                PM23SI2.setMatakuliah(matMobile);
            }

            KelasMataKuliah PM23SI3 = r.where(KelasMataKuliah.class).equalTo("kelasmatakuliahID", 1).findFirst();
            if (PM23SI3 == null) {
                PM23SI3 = r.createObject(KelasMataKuliah.class, 1);
                PM23SI3.setKelas("23SI3");
                PM23SI3.setDosen("Sir Ade");
                PM23SI3.setMatakuliah(matMobile);
            }

            KelasMataKuliah PBO23SI2 = r.where(KelasMataKuliah.class).equalTo("kelasmatakuliahID", 2).findFirst();
            if (PBO23SI2 == null) {
                PBO23SI2 = r.createObject(KelasMataKuliah.class, 2);
                PBO23SI2.setKelas("23SI2");
                PBO23SI2.setDosen("Sir Ade");
                PBO23SI2.setMatakuliah(matPBO);
            }

            KelasMataKuliah PBO23SI3 = r.where(KelasMataKuliah.class).equalTo("kelasmatakuliahID", 3).findFirst();
            if (PBO23SI3 == null) {
                PBO23SI3 = r.createObject(KelasMataKuliah.class, 3);
                PBO23SI3.setKelas("23SI3");
                PBO23SI3.setDosen("Sir Ade");
                PBO23SI3.setMatakuliah(matPBO);
            }


            Mahasiswa mhs1 = r.where(Mahasiswa.class).equalTo("studentID", 1).findFirst();
            if (mhs1 == null) {
                mhs1 = r.createObject(Mahasiswa.class, 1);
                mhs1.setNama("Andi");
                mhs1.setJenisKelamin("Laki-Laki");
                mhs1.setProdi(prodiSI);
            }

            Mahasiswa mhs2 = r.where(Mahasiswa.class).equalTo("studentID", 2).findFirst();
            if (mhs2 == null) {
                mhs2 = r.createObject(Mahasiswa.class, 2);
                mhs2.setNama("Ayu");
                mhs2.setJenisKelamin("Perempuan");
                mhs2.setProdi(prodiSI);
            }


            KRS krs1 = r.where(KRS.class).equalTo("KRSID", 0).findFirst();
            if (krs1 == null) {
                krs1 = r.createObject(KRS.class, 0);
                krs1.setSemester("3");
                krs1.setMahasiswa(mhs1);
                krs1.setKrsDetail(new RealmList<>());
            }

            KRS krs2 = r.where(KRS.class).equalTo("KRSID", 1).findFirst();
            if (krs2 == null) {
                krs2 = r.createObject(KRS.class, 1);
                krs2.setSemester("3");
                krs2.setMahasiswa(mhs2);
                krs2.setKrsDetail(new RealmList<>());
            }


            KRSDetail detail1 = r.where(KRSDetail.class).equalTo("krsDetailID", 0).findFirst();
            if (detail1 == null) {
                detail1 = r.createObject(KRSDetail.class, 0);
                detail1.setStatus("Lulus");
                detail1.setNilai("A");
                detail1.setKelasMataKuliah(PBO23SI2);
            }

            KRSDetail detail2 = r.where(KRSDetail.class).equalTo("krsDetailID", 1).findFirst();
            if (detail2 == null) {
                detail2 = r.createObject(KRSDetail.class, 1);
                detail2.setStatus("Lulus");
                detail2.setNilai("A");
                detail2.setKelasMataKuliah(PM23SI2);
            }


            if (!krs1.getKrsDetail().contains(detail1)) {
                krs1.getKrsDetail().add(detail1);
            }
            if (!krs1.getKrsDetail().contains(detail2)) {
                krs1.getKrsDetail().add(detail2);
            }


            KRSDetail detail3 = r.where(KRSDetail.class).equalTo("krsDetailID", 2).findFirst();
            if (detail3 == null) {
                detail3 = r.createObject(KRSDetail.class, 2);
                detail3.setStatus("Sedang Diambil");
                detail3.setNilai("-");
                detail3.setKelasMataKuliah(PM23SI3);
            }

            KRSDetail detail4 = r.where(KRSDetail.class).equalTo("krsDetailID", 3).findFirst();
            if (detail4 == null) {
                detail4 = r.createObject(KRSDetail.class, 3);
                detail4.setStatus("Sedang Diambil");
                detail4.setNilai("-");
                detail4.setKelasMataKuliah(PBO23SI3);
            }


            if (!krs2.getKrsDetail().contains(detail3)) {
                krs2.getKrsDetail().add(detail3);
            }
            if (!krs2.getKrsDetail().contains(detail4)) {
                krs2.getKrsDetail().add(detail4);
            }
        });

        Toast.makeText(this, "Data tersimpan", Toast.LENGTH_SHORT).show();
    }


    public void toProfil(){
        Intent intent = new Intent(this,ProfilActivity.class);
        intent.putExtra("nama",edtNama.getText().toString());
        startActivity(intent);
    }
    public void toDashboard(){
        Intent intent = new Intent(this,DashboardActivity.class);
        startActivity(intent);
    }
}