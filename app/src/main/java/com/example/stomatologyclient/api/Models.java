package com.example.stomatologyclient.api;

import com.example.stomatologyclient.models.NamedModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Список моделей для Retrofit.
 * Наследование от NamedModel  нужно, чтобы
 * пихать в адаптер.
 *
 * Это универсальный адаптер, так что немного говнокодно -
 * NamedModel содержит поля на все случаи жизини:
 *  - картинку
 *  - название
 *  - стоимость
 */
public class Models
{
    static DateFormat df = new SimpleDateFormat("dd.MM.yyyy");


    //Преобразует ФИО в строку, исключая отчество, если его нет
    private static String convertFIOToName(PersonInfo personInfo)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(personInfo.Surname).append(" ")
                .append(personInfo.Name);

        if(personInfo.Middlename!=null)
            sb.append(" ").append(personInfo.Middlename);

        return  sb.toString();
    }

    /**
     * Категория
     */
    public static class Category  extends NamedModel
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
    public static class Subcategory
    {
        public String Name;
        public Integer CategoryId;
        public List<Procedure> Procedures = new ArrayList<Procedure>();
        public int Id;

    }

    /**
     * Процедура
     */
    public static class Procedure
    {
        public String Name;
        public String Description;
        public Integer SubcategoryId;
        public Integer Id;
        public String Image;
        public Double Cost;
    }

    /**
     * Инфа о человеке
     */
    public static class PersonInfo
    {

        public String Surname;
        public String Name;
        public String Middlename;
        public Integer Id;

    }

    /**
     * Инфа о клинике
     */
    public static class ClinicInfo {

        public String Name;
        public String PhoneNumber;
        public String Email;
        public String Adress;
        public Integer Id;

    }

    /**
     * Зубной техник
     */
    public static class DentalTechnican extends NamedModel
    {

        public PersonInfo PersonInfo;
        public Integer ApplicationUserId;
        public Integer PersonInfoId;


        @Override
        public String Name() {
            return Models.convertFIOToName(PersonInfo);
        }
    }

    /**
     * Врач
     */
    public static class Doctor extends NamedModel
    {

        public PersonInfo PersonInfo;
        public String Text;
        public Integer ApplicationUserId;
        public Integer PersonInfoId;

        @Override
        public String Name() {
            return Models.convertFIOToName(PersonInfo);
        }
    }

    /**
     * Пациент
     */
    public static class Patient extends NamedModel {

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
            return Models.convertFIOToName(PersonInfo);
        }
    }

    /**
     * Посешение
     */
    public static class Visit extends NamedModel {

        public Doctor Doctor;
        public java.util.Date Date;
        public String Annotation;
        public Integer PatientId;
        public Integer DoctorId;
        public List<Object> Procedures = new ArrayList<Object>();

        @Override
        public String Name() {
            return df.format(Date);
        }
    }

    /**
     * Наряд-заказ
     */
    public static class Order  extends  NamedModel{

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

    /**
     * Элемент наряд заказа - работа по зубу
     */
    public static class Tooth {

        public Integer ToothNo;
        public Integer ProcedureId;
        public TechnicanProcedure Procedure;
        public Integer OrderId;
        public Integer Id;
    }

    /**
     * То, что может изготовить техние
     */
    public static class TechnicanProcedure {

        public String Name;
        public Integer Cost;
        public Integer Id;

    }

    // РЕГИСТРАЦИЯ

    public static class PatientRegistrationViewModel {

        public Integer Age;
        public Integer MedicalCardNumber;
        public Boolean IsMen;
        public String Email;
        public String Password;
        public String ConfirmPassword;
        public String Name;
        public String Surname;
        public String Middlename;

    }

    public static class DoctorRegistrationViewModel {

        public Integer Age;
        public String Email;
        public String Password;
        public String ConfirmPassword;
        public String Name;
        public String Surname;
        public String Middlename;
        public String Image;
        public String Description;

    }

    public static class TechnicanRegistrationViewModel {

        public Integer Age;
        public String Email;
        public String Password;
        public String ConfirmPassword;
        public String Name;
        public String Surname;
        public String Middlename;

    }
}