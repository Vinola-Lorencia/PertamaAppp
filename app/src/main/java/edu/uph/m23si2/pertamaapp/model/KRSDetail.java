package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KRSDetail extends RealmObject {
    @PrimaryKey
    private int krsDetailID;

    private String Status, Nilai;
    private KRS krs;
    private KelasMataKuliah kelasMataKuliah;
    public KRSDetail(){}

    public KRSDetail(int krsDetailID, int semester, String status, String nilai, KRS krs, KelasMataKuliah kelasMataKuliah) {
        this.krsDetailID = krsDetailID;

        Status = status;
        Nilai = nilai;
        this.krs = krs;
        this.kelasMataKuliah = kelasMataKuliah;
    }

    public int getKrsDetailID() {
        return krsDetailID;
    }

    public void setKrsDetailID(int krsDetailID) {
        this.krsDetailID = krsDetailID;
    }



    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getNilai() {
        return Nilai;
    }

    public void setNilai(String nilai) {
        Nilai = nilai;
    }

    public KRS getKrs() {
        return krs;
    }

    public void setKrs(KRS krs) {
        this.krs = krs;
    }

    public KelasMataKuliah getKelasMataKuliah() {
        return kelasMataKuliah;
    }

    public void setKelasMataKuliah(KelasMataKuliah kelasMataKuliah) {
        this.kelasMataKuliah = kelasMataKuliah;
    }
}
