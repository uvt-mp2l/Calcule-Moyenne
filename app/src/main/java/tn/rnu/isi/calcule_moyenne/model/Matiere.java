package tn.rnu.isi.calcule_moyenne.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wim on 4/26/16.
 */
public class Matiere implements Parcelable {

    private int id;
    private String name;
    private String ds_note;
    private String ds_coef;
    private String tp_note;
    private String tp_coef;
    private String exm_note;
    private String exm_coef;

    public Matiere() {
    }

    protected Matiere(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ds_note = in.readString();
        this.ds_coef = in.readString();
        this.tp_note = in.readString();
        this.tp_coef = in.readString();
        this.exm_note = in.readString();
        this.exm_coef = in.readString();
    }

    public static final Parcelable.Creator<Matiere> CREATOR = new Parcelable.Creator<Matiere>() {
        @Override
        public Matiere createFromParcel(Parcel source) {
            return new Matiere(source);
        }

        @Override
        public Matiere[] newArray(int size) {
            return new Matiere[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDs_note() {
        return ds_note;
    }

    public void setDs_note(String ds_note) {
        this.ds_note = ds_note;
    }

    public String getDs_coef() {
        return ds_coef;
    }

    public void setDs_coef(String ds_coef) {
        this.ds_coef = ds_coef;
    }

    public String getTp_note() {
        return tp_note;
    }

    public void setTp_note(String tp_note) {
        this.tp_note = tp_note;
    }

    public String getTp_coef() {
        return tp_coef;
    }

    public void setTp_coef(String tp_coef) {
        this.tp_coef = tp_coef;
    }

    public String getExm_note() {
        return exm_note;
    }

    public void setExm_note(String exm_note) {
        this.exm_note = exm_note;
    }

    public String getExm_coef() {
        return exm_coef;
    }

    public void setExm_coef(String exm_coef) {
        this.exm_coef = exm_coef;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.ds_note.toString());
        dest.writeString(this.ds_coef.toString());
        dest.writeString(this.tp_note.toString());
        dest.writeString(this.tp_coef.toString());
        dest.writeString(this.exm_note.toString());
        dest.writeString(this.exm_coef.toString());
    }

}