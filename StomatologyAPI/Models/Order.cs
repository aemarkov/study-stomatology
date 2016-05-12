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
        public int PatientID { get; set; }
        //public Patient Patient { get; set; }


        //Зубной техник
        [Required]
        public int DentalTechnicanID { get; set; }
        public DentalTechnican Technican { get; set; }


        //Информация о клинике
        [Required]
        public int ClinicInfoID { get; set; }
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