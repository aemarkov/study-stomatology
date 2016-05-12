using System.Security.Claims;
using System.Threading.Tasks;
using Microsoft.AspNet.Identity;
using Microsoft.AspNet.Identity.EntityFramework;
using Microsoft.AspNet.Identity.Owin;
using System.ComponentModel.DataAnnotations;

namespace StomatologyAPI.Models
{
    // Чтобы добавить данные профиля для пользователя, можно добавить дополнительные свойства в класс ApplicationUser. Дополнительные сведения см. по адресу: http://go.microsoft.com/fwlink/?LinkID=317594.
    public class ApplicationUser : IdentityUser
    {
        /// <summary>
        /// Фамилия
        /// </summary>
        [Required]
        [MaxLength(64)]
        public string Surname { get; set; }

        /// <summary>
        /// Имя
        /// </summary>
        [Required]
        [MaxLength(64)]
        public string Name { get; set; }

        /// <summary>
        /// Отчество
        /// </summary>
        [MaxLength(64)]
        public string Middlename { get; set; }

        /// <summary>
        /// Конструктор по умолчанию
        /// </summary>
        public ApplicationUser() { }

        public ApplicationUser(string surname, string name, string middlename=null)
        {
            Surname = surname;
            Name = name;
            Middlename = middlename;
        }

        public async Task<ClaimsIdentity> GenerateUserIdentityAsync(UserManager<ApplicationUser> manager, string authenticationType)
        {
            // Обратите внимание, что authenticationType должен совпадать с типом, определенным в CookieAuthenticationOptions.AuthenticationType
            var userIdentity = await manager.CreateIdentityAsync(this, authenticationType);
            // Здесь добавьте настраиваемые утверждения пользователя
            return userIdentity;
        }
    }

   

}