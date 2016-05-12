using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    public class Patient:ApplicationUser
    {
        /// <summary>
		/// Номер полиса
		/// </summary>
        public int? MedicalCardNumber { get; set; }

        /// <summary>
		/// Возраст
		/// </summary>
        [Required]
        [Range(0, int.MaxValue)]
        public int Age { get; set; }

        /// <summary>
        /// Пол. Я до ужаса нетолерантный.
        /// </summary>
        [Required]
        [Display(Name = "Пол")]
        public bool IsMen { get; set; }


        /// <summary>
        /// Посещения
        /// </summary>
        public ICollection<PatientVisit> Visits
        {
            get { return this.visits ?? (this.visits = new List<PatientVisit>()); }
            set { visits = value; }
        }
        private ICollection<PatientVisit> visits;

        /// <summary>
        /// Наряд-заказы
        /// </summary>
        public ICollection<Order> Orders
        {
            get { return this.orders ?? (this.orders = new List<Order>()); }
            set { orders = value; }
        }
        private ICollection<Order> orders;


        /////////////////////////////////// КОНСТРУКТОРЫ //////////////////////////////////////////
        public Patient() { }
        public Patient(string surname, string name, string middlename = null) : base(surname, name, middlename)
        {
        }
    }

   
}