using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
	/// Посещения пациентом врача
	/// </summary>
    public class PatientVisit:AbstractModel
    {
        /// <summary>
		/// Дата посещения
		/// </summary>
        public DateTime? Date { get; set; }

        /// <summary>
        /// Примечания
        /// </summary>
        public string Annotation { get; set; }

		/// <summary>
		/// Завершенное посещение нельзя редактировать
		/// </summary>
		public bool IsClosed { get; set; }

        //Пациент
        [Required]
        public int PatientId { get; set; }
        public virtual Patient Patient { get; set; }

        //Врач
        public int? DoctorId { get; set; }
        public virtual Doctor Doctor { get; set; }
        
        /// <summary>
        /// Выполненные процедуры
        /// </summary>
        public ICollection<Procedure> Procedures
        {
            get { return procedures ?? (procedures = new List<Procedure>()); }
            set { procedures = value; }
        }
        private ICollection<Procedure> procedures;
    }
}