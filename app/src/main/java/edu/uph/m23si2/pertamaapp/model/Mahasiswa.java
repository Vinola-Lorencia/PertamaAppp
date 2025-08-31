package edu.uph.m23si2.pertamaapp.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Mahasiswa extends RealmObject {
    @PrimaryKey
    private int studentID;
    private String Nama,JenisKelamin,Hobi,Email,Prodi;
    private Prodi prodi;

    public Mahasiswa(){}

    public Mahasiswa(int studentID, String nama, String jenisKelamin, String hobi, String email, String prodi, Prodi prodi1) {
        this.studentID = studentID;
        Nama = nama;
        JenisKelamin = jenisKelamin;
        Hobi = hobi;
        Email = email;
        Prodi = prodi;
        this.prodi = prodi1;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getHobi() {
        return Hobi;
    }

    public void setHobi(String hobi) {
        Hobi = hobi;
    }

    public String getJenisKelamin() {
        return JenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        JenisKelamin = jenisKelamin;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getProdi() {
        return Prodi;
    }

    public void setProdi(String prodi) {
        Prodi = prodi;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setProdi(Prodi prodi) {
        this.prodi = prodi;
    }

    @Override
    public String toString() {
        return "Mahasiswa{" +
                "Email='" + Email + '\'' +
                ", studentID=" + studentID +
                ", Nama='" + Nama + '\'' +
                ", JenisKelamin='" + JenisKelamin + '\'' +
                ", Hobi='" + Hobi + '\'' +
                ", Prodi='" + Prodi + '\'' +
                '}';
    }
}
