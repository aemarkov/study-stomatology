using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
	/// Информация о клинике
	/// (Контактная инфа)
	/// </summary>
    public class ClinicInfo:AbstractModel
    {

        /// <summary>
		/// Название клиники
		/// </summary>
        [Required]
        public string Name { get; set; }

        /// <summary>
        /// Номер телефона
        /// </summary>
        public string PhoneNumber { get; set; }

        /// <summary>
        /// Email
        /// </summary>
        public string Email { get; set; }

        /// <summary>
        /// Адресс клиники
        /// </summary>
        public string Adress { get; set; }
    }
}