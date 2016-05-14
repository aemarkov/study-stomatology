using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models.BindingModels
{
    /// <summary>
    /// Модель для сохранения только пациента
    /// </summary>
    public class PatientBindingModel
    {
        public int Id;

        [Display(Name = "Возраст")]
        [Required]
        [Range(0, int.MaxValue)]
        public int Age { get; set; }

        [Display(Name = "Номер полиса медицинского страхования")]
        public int? MedicalCardNumber { get; set; }

        [Required]
        [Display(Name = "Пол")]
        public Boolean IsMen { get; set; }

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
}