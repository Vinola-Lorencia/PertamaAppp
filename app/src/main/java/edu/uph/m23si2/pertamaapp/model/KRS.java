package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class KRS extends RealmObject {
    @PrimaryKey
    private int KRSID;
    private String semester;
    private Mahasiswa mahasiswa;
    private RealmList<KRSDetail> krsDetail;

    public KRS (){}

    public KRS(int KRSID, String semester, Mahasiswa mahasiswa, RealmList<KRSDetail> krsDetail) {
        this.KRSID = KRSID;
        this.semester = semester;
        this.mahasiswa = mahasiswa;
        this.krsDetail = krsDetail;
    }

    public int getKRSID() {
        return KRSID;
    }

    public void setKRSID(int KRSID) {
        this.KRSID = KRSID;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public RealmList<KRSDetail> getKrsDetail() {
        return krsDetail;
    }

    public void setKrsDetail(RealmList<KRSDetail> krsDetail) {
        this.krsDetail = krsDetail;
    }
}
