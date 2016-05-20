using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
    /// Наряд-заказ
    /// Это документ, который создается для изготовления протезов и прочего
    /// и передатется зубному технику
    /// </summary>
    public class Order:AbstractModel
    {
        /// <summary>
        /// Дата закаха
        /// </summary>
        public DateTime? Date { get; set; }

        /// <summary>
        /// Завершен??
        /// </summary>
        public bool IsFinished { get; set; }

        /// <summary>
        /// Примечание
        /// </summary>
        public String Annotation { get; set; }

        //Пациент
        [Required]
        public int PatientId { get; set; }
        public virtual Patient Patient { get; set; }

		/// <summary>
		/// Завершенный заказ нельзя редактировать
		/// </summary>
		public bool IsClosed { get; set; }

		//Пациент
		public int? DoctorId { get; set; }
        public virtual Doctor Doctor { get; set; }


        //Зубной техник
        public int? DentalTechnicanId { get; set; }
        public virtual DentalTechnican DentalTechnican { get; set; }


        //Информация о клинике
        [Required]
        public int ClinicInfoId { get; set; }
        public ClinicInfo ClinicInfo { get; set; }

        /// <summary>
        /// Что делать для конкретного зуба
        /// </summary>
        public ICollection<ToothWork> Teeth
        {
            get { return this.teeth ?? (this.teeth = new List<ToothWork>()); }
            set { teeth = value; }
        }
        private ICollection<ToothWork> teeth;
    }
}