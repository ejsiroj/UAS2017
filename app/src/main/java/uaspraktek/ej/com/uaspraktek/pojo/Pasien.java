package uaspraktek.ej.com.uaspraktek.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by EJ Public on 13/12/2017.
 */

public class Pasien implements Parcelable {
    String patientId;
    int patientCardNumber;
    String patientName;
    Date patientDOB;
    Date patientRedeemTime;
    String patientAddress;
    String patientGender;

    public Pasien() {
        super();
    }

    public Pasien(String patientId, int patientCardNumber, String patientName, Date patientDOB, Date patientRedeemTime, String patientAddress, String patientGender) {
        this.patientId = patientId;
        this.patientCardNumber = patientCardNumber;
        this.patientName = patientName;
        this.patientDOB = patientDOB;
        this.patientRedeemTime = patientRedeemTime;
        this.patientAddress = patientAddress;
        this.patientGender = patientGender;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public int getPatientCardNumber() {
        return patientCardNumber;
    }

    public void setPatientCardNumber(int patientCardNumber) {
        this.patientCardNumber = patientCardNumber;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Date getPatientDOB() {
        return patientDOB;
    }

    public void setPatientDOB(Date patientDOB) {
        this.patientDOB = patientDOB;
    }

    public Date getPatientRedeemTime() {
        return patientRedeemTime;
    }

    public void setPatientRedeemTime(Date patientRedeemTime) {
        this.patientRedeemTime = patientRedeemTime;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.patientId);
        dest.writeInt(this.patientCardNumber);
        dest.writeString(this.patientName);
        dest.writeLong(this.patientDOB != null ? this.patientDOB.getTime() : -1);
        dest.writeLong(this.patientRedeemTime != null ? this.patientRedeemTime.getTime() : -1);
        dest.writeString(this.patientAddress);
        dest.writeString(this.patientGender);
    }

    protected Pasien(Parcel in) {
        this.patientId = in.readString();
        this.patientCardNumber = in.readInt();
        this.patientName = in.readString();
        long tmpPatientDOB = in.readLong();
        this.patientDOB = tmpPatientDOB == -1 ? null : new Date(tmpPatientDOB);
        long tmpPatientRedeemTime = in.readLong();
        this.patientRedeemTime = tmpPatientRedeemTime == -1 ? null : new Date(tmpPatientRedeemTime);
        this.patientAddress = in.readString();
        this.patientGender = in.readString();
    }

    public static final Creator<Pasien> CREATOR = new Creator<Pasien>() {
        @Override
        public Pasien createFromParcel(Parcel source) {
            return new Pasien(source);
        }

        @Override
        public Pasien[] newArray(int size) {
            return new Pasien[size];
        }
    };
}
