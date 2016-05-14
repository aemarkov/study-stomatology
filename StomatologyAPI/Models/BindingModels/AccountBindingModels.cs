using System;
using System.ComponentModel.DataAnnotations;
using Newtonsoft.Json;

namespace StomatologyAPI.Models
{
    // Модели, используемые в качестве параметров действий AccountController.

    public class AddExternalLoginBindingModel
    {
        [Required]
        [Display(Name = "Внешний маркер доступа")]
        public string ExternalAccessToken { get; set; }
    }

    public class ChangePasswordBindingModel
    {
        [Required]
        [DataType(DataType.Password)]
        [Display(Name = "Текущий пароль")]
        public string OldPassword { get; set; }

        [Required]
        [StringLength(100, ErrorMessage = "Значение {0} должно содержать не менее {2} символов.", MinimumLength = 6)]
        [DataType(DataType.Password)]
        [Display(Name = "Новый пароль")]
        public string NewPassword { get; set; }

        [DataType(DataType.Password)]
        [Display(Name = "Подтверждение нового пароля")]
        [Compare("NewPassword", ErrorMessage = "Новый пароль и его подтверждение не совпадают.")]
        public string ConfirmPassword { get; set; }
    }

    /// <summary>
    /// Модель регистрации
    /// </summary>
    public class RegisterBindingModel
    {
        [Required]
        [Display(Name = "Адрес электронной почты")]
        public string Email { get; set; }

        [Required]
        [StringLength(100, ErrorMessage = "Значение {0} должно содержать не менее {2} символов.", MinimumLength = 6)]
        [DataType(DataType.Password)]
        [Display(Name = "Пароль")]
        public string Password { get; set; }

        [DataType(DataType.Password)]
        [Display(Name = "Подтверждение пароля")]
        [Compare("Password", ErrorMessage = "Пароль и его подтверждение не совпадают.")]
        public string ConfirmPassword { get; set; }

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
    /// Регистрация пациента
    /// </summary>
    public class PatientRegisterBindingModel : RegisterBindingModel
    {
        [Display(Name = "Возраст")]
        [Required]
        [Range(0, int.MaxValue)]
        public int Age { get; set; }

        [Display(Name = "Номер полиса медицинского страхования")]
        public int? MedicalCardNumber { get; set; }

        [Required]
        [Display(Name = "Пол")]
        public Boolean IsMen { get; set; }
    }

    /// <summary>
    /// Регистрация врача
    /// </summary>
    public class DoctorRegisterBindingModel : RegisterBindingModel
    {
        [Display(Name = "Изображение")]
        [MaxLength(255)]
        public String Image { get; set; }

        [Display(Name = "Описание")]
        public String Text { get; set; }
    }

    public class RegisterExternalBindingModel
    {
        [Required]
        [Display(Name = "Адрес электронной почты")]
        public string Email { get; set; }
    }

    public class RemoveLoginBindingModel
    {
        [Required]
        [Display(Name = "Поставщик входа")]
        public string LoginProvider { get; set; }

        [Required]
        [Display(Name = "Ключ поставщика")]
        public string ProviderKey { get; set; }
    }

    public class SetPasswordBindingModel
    {
        [Required]
        [StringLength(100, ErrorMessage = "Значение {0} должно содержать не менее {2} символов.", MinimumLength = 6)]
        [DataType(DataType.Password)]
        [Display(Name = "Новый пароль")]
        public string NewPassword { get; set; }

        [DataType(DataType.Password)]
        [Display(Name = "Подтверждение нового пароля")]
        [Compare("NewPassword", ErrorMessage = "Новый пароль и его подтверждение не совпадают.")]
        public string ConfirmPassword { get; set; }
    }
}
