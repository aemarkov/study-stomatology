using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
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

        //Пациент
        [Required]
        public int PatientID { get; set; }

        //Врач
        public int? DoctorID { get; set; }
        
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