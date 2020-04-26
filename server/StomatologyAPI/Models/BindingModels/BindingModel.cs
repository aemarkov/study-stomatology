using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models.BindingModels
{
    /*
     * Эти модели служат для выполнения CRUD операций
     * с врачом, пациентом, техником. Именно для взаимодействия с
     * этими классами + PersonInfo БЕЗ задействия Identity
     */

    public class BaseBindingModel
    {
        public int Id;


        [Required]
        [MaxLength(64)]
        [Display(Name = "Имя")]
        public string Name { get; set; }
        [Required]
        [MaxLength(64)]
        [Display(Name = "Фамилия")]
        public string Surname { get; set; }

        [MaxLength(64)]
        [Display(Name = "Отчество")]
        public string Middlename { get; set; }
    }


    /// <summary>
    /// Модель для сохранения только пациента
    /// </summary>
    public class PatientBindingModel:BaseBindingModel
    {
        [Display(Name = "Дата рождения")]
        public DateTime DateOfBirth { get; set; }

        [Display(Name = "Номер полиса медицинского страхования")]
        public int? MedicalCardNumber { get; set; }

        [Required]
        [Display(Name = "Пол")]
        public Boolean IsMen { get; set; }
    }

    /// <summary>
    /// Модель для сохранения врача
    /// </summary>
    public class DoctorBindingModel:BaseBindingModel
    {
        [Display(Name = "Изображение")]
        [MaxLength(255)]
        public String Image { get; set; }

        [Display(Name = "Описание")]
        public String Text { get; set; }
    }
}