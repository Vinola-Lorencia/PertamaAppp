package edu.uph.m23si2.pertamaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import edu.uph.m23si2.pertamaapp.model.KRS;
import edu.uph.m23si2.pertamaapp.model.KRSDetail;
import edu.uph.m23si2.pertamaapp.model.KelasMataKuliah;
import edu.uph.m23si2.pertamaapp.model.Mahasiswa;
import edu.uph.m23si2.pertamaapp.model.Matakuliah;
import edu.uph.m23si2.pertamaapp.model.Prodi;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText edtNama, edtPassword;

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
    }
    public void initData(){
        Realm realm =  Realm.getDefaultInstance();
        realm.executeTransaction(r -> {
            Number maxId = r.where(Mahasiswa.class).max("studentID");
            Prodi prodiSI = r.createObject(Prodi.class, 0);
            prodiSI.setFakultas("Fakultas Teknologi Informasi");
            prodiSI.setNama("Sistem Informasi");

            Matakuliah matMobile = r.createObject(Matakuliah.class, 0);
            matMobile.setNama("Pemograman Mobile Lanjut");
            matMobile.setSks(3);
            matMobile.setProdi(prodiSI);

            Matakuliah matPBO = r.createObject(Matakuliah.class, 2);
            matMobile.setNama("Pemograman Berorientasi Objek");
            matMobile.setSks(3);
            matMobile.setProdi(prodiSI);

            KelasMataKuliah PM23SI2 = r.createObject(KelasMataKuliah.class,0);
            PM23SI2.setKelas("23SI2");
            PM23SI2.setDosen("Sir Ade");
            PM23SI2.setMatakuliah(matMobile);

            KelasMataKuliah PM23SI3 = r.createObject(KelasMataKuliah.class,1);
            PM23SI3.setKelas("23SI3");
            PM23SI3.setDosen("Sir Ade");
            PM23SI3.setMatakuliah(matMobile);

            KelasMataKuliah PBO23SI2 = r.createObject(KelasMataKuliah.class,2);
            PBO23SI2.setKelas("23SI2");
            PBO23SI2.setDosen("Sir Ade");
            PBO23SI2.setMatakuliah(matPBO);

            KelasMataKuliah PBO23SI3 = r.createObject(KelasMataKuliah.class,3);
            PBO23SI3.setKelas("23SI3");
            PBO23SI3.setDosen("Sir Ade");
            PBO23SI3.setMatakuliah(matPBO);

            Mahasiswa mhs1 = r.createObject(Mahasiswa.class, 1);
            mhs1.setNama("Andi");
            mhs1.setJenisKelamin("laki-Laki");
            mhs1.setProdi(prodiSI);

            Mahasiswa mhs2 = r.createObject(Mahasiswa.class, 2);
            mhs2.setNama("Ayu");
            mhs2.setJenisKelamin("Perempuan");
            mhs2.setProdi(prodiSI);

            KRS krs1 = r.createObject(KRS.class, 0);
            krs1.setSemester("3");
            krs1.setMahasiswa(mhs1);

            KRS krs2 = r.createObject(KRS.class, 1);
            krs2.setSemester("3");
            krs2.setMahasiswa(mhs2);


            KRSDetail detail1 = r.createObject(KRSDetail.class,0);
            detail1.setStatus("Lulus");
            detail1.setNilai("A");
            detail1.setKelasMataKuliah(PBO23SI2);

            KRSDetail detail2 = r.createObject(KRSDetail.class,1);
            detail2.setStatus("Lulus");
            detail2.setNilai("A");
            detail2.setKelasMataKuliah(PM23SI2);

            RealmList<KRSDetail> listDetail1 = new RealmList<>();
            listDetail1.add(detail1);
            listDetail1.add(detail2);
            krs1.setKrsDetail(listDetail1);

            KRSDetail detail3 = r.createObject(KRSDetail.class,2);
            detail3.setStatus("Sedang Diambil");
            detail3.setNilai("-");
            detail3.setKelasMataKuliah(PM23SI3);

            KRSDetail detail4 = r.createObject(KRSDetail.class,3);
            detail4.setStatus("Sedang Diambil");
            detail4.setNilai("-");
            detail4.setKelasMataKuliah(PBO23SI3);

            RealmList<KRSDetail> listDetail2 = new RealmList<>();
            listDetail2.add(detail3);
            listDetail2.add(detail4);
            krs2.setKrsDetail(listDetail2);




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