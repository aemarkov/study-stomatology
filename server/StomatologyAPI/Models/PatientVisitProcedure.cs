using StomatologyAPI.Models.Abstract;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models
{
    /// <summary>
    /// таблица для связи PatientVisit и Procedure
    /// Стандартная не годится тем, что не поддерживает
    /// дублирующиеся связи
    /// </summary>
    [Table("PatientVisitProcedureMapping")]
    public class PatientVisitProcedureMapping:AbstractModel
    {
        public int PatientVisitId { get; set; }
        public int ProcedureId { get; set; }
    }
}