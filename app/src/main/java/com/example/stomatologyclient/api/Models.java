package com.example.stomatologyclient.api;

import com.example.stomatologyclient.models.NamedModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Garrus on 15.05.2016.
 */
public class Models
{
    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");


    /**
     * Категория
     */
    public class Category  extends NamedModel
    {
        public String Name;
        public String Description;
        public List<Subcategory> Subcategories = new ArrayList<Subcategory>();

        @Override
        public String Name() {
            return Name;
        }
    }

    /**
     * Подкатегория
     */
    public class Subcategory extends NamedModel
    {
        public String Name;
        public Integer CategoryId;
        public List<Procedure> Procedures = new ArrayList<Procedure>();

        public  String Name(){return Name;}
    }

    /**
     * Процедура
     */
    public class Procedure extends NamedModel
    {
        public String Name;
        public String Description;
        public Integer SubcategoryId;

        @Override
        public String Name() {
            return Name;
        }
    }

    /**
     * Инфа о человеке
     */
    public class PersonInfo
    {

        public String Surname;
        public String Name;
        public String Middlename;
        public Integer Id;

    }

    /**
     * Инфа о клинике
     */
    public class ClinicInfo {

        public String Name;
        public String PhoneNumber;
        public String Email;
        public String Adress;
        public Integer Id;

    }

    /**
     * Зубной техник
     */
    public class DentalTechnican extends NamedModel
    {

        public PersonInfo PersonInfo;
        public Integer ApplicationUserId;
        public Integer PersonInfoId;


        @Override
        public String Name() {
            return PersonInfo.Surname+" "+PersonInfo.Name+" "+PersonInfo.Middlename;
        }
    }

    /**
     * Врач
     */
    public class Doctor extends NamedModel
    {

        public PersonInfo PersonInfo;
        public String Text;
        public Integer ApplicationUserId;
        public Integer PersonInfoId;

        @Override
        public String Name() {
            return PersonInfo.Surname+" "+PersonInfo.Name+" "+PersonInfo.Middlename;
        }
    }

    /**
     * Пациент
     */
    public class Patient extends NamedModel {

        public PersonInfo PersonInfo;
        public Integer MedicalCardNumber;
        public Integer Age;
        public Boolean IsMen;
        public Integer ApplicationUserId;
        public Integer PersonInfoId;
        public List<Visit> Visits = new ArrayList<Visit>();
        public List<Order> Orders = new ArrayList<Order>();

        @Override
        public String Name() {
            return PersonInfo.Surname+" "+PersonInfo.Name+" "+PersonInfo.Middlename;
        }
    }

    /**
     * Посешение
     */
    public class Visit extends NamedModel {

        public Doctor Doctor;
        public java.util.Date Date;
        public String Annotation;
        public Integer PatientId;
        public Integer DoctorId;
        public List<Object> Procedures = new ArrayList<Object>();
        public Integer Id;

        @Override
        public String Name() {
            return df.format(Date);
        }
    }

    /**
     * Наряд-заказ
     */
    public class Order  extends  NamedModel{

        public DentalTechnican DentalTechnican;
        public Doctor Doctor;
        public ClinicInfo ClinicInfo;
        public java.util.Date Date;
        public Boolean IsFinished;
        public String Annotation;

        public Integer PatientId;
        public Integer DoctorId;
        public Integer DentalTechnicanId;
        public Integer ClinicInfoId;

        public List<Object> Teeth = new ArrayList<Object>();

        @Override
        public String Name() {
            return df.format(Date);
        }

    }


    public class Tooth {

        public Integer ToothNo;
        public Integer ProcedureId;
        public TechnicanProcedure Procedure;
        public Integer OrderId;
        public Integer Id;
    }

    public class TechnicanProcedure {

        public String Name;
        public Integer Cost;
        public Integer Id;

    }
}
