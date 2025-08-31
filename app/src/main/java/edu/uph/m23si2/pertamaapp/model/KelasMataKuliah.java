package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KelasMataKuliah extends RealmObject {
    @PrimaryKey
    private int kelasmatakuliahID;
    private String Kelas, Dosen;
    private Matakuliah matakuliah;
    private RealmList<KRSDetail> krsdetail;
    public KelasMataKuliah(){}

    public KelasMataKuliah(String kelas, int kelasmatakuliahID, String dosen, Matakuliah matakuliah, RealmList<KRSDetail> krsdetail) {
        Kelas = kelas;
        this.kelasmatakuliahID = kelasmatakuliahID;
        Dosen = dosen;
        this.matakuliah = matakuliah;
        this.krsdetail = krsdetail;
    }

    public int getKelasmatakuliahID() {
        return kelasmatakuliahID;
    }

    public void setKelasmatakuliahID(int kelasmatakuliahID) {
        this.kelasmatakuliahID = kelasmatakuliahID;
    }

    public String getKelas() {
        return Kelas;
    }

    public void setKelas(String kelas) {
        Kelas = kelas;
    }

    public String getDosen() {
        return Dosen;
    }

    public void setDosen(String dosen) {
        Dosen = dosen;
    }

    public Matakuliah getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(Matakuliah matakuliah) {
        this.matakuliah = matakuliah;
    }

    public RealmList<KRSDetail> getKrsdetail() {
        return krsdetail;
    }

    public void setKrsdetail(RealmList<KRSDetail> krsdetail) {
        this.krsdetail = krsdetail;
    }
}
