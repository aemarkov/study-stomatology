using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models.BindingModels
{
    /// <summary>
    /// Модель для работы с процедурами в посещении
    /// </summary>
    public class VisitProcedureBindingModel
    {
        /// <summary>
        /// ID посещения
        /// </summary>
        [Required]
        public int VisitId { get; set; }

        /// <summary>
        /// ID процедуры
        /// </summary>
        [Required]
        public int ProcedureId { get; set; }
    }
}