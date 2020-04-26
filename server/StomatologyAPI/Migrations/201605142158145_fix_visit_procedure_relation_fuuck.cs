namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class fix_visit_procedure_relation_fuuck : DbMigration
    {
        public override void Up()
        {
            RenameTable(name: "dbo.PatientVisitProcedure", newName: "PatientVisitProcedures");
            RenameColumn(table: "dbo.PatientVisitProcedures", name: "PatientVisitId", newName: "PatientVisit_Id");
            RenameColumn(table: "dbo.PatientVisitProcedures", name: "ProcedureId", newName: "Procedure_Id");
            RenameIndex(table: "dbo.PatientVisitProcedures", name: "IX_PatientVisitId", newName: "IX_PatientVisit_Id");
            RenameIndex(table: "dbo.PatientVisitProcedures", name: "IX_ProcedureId", newName: "IX_Procedure_Id");
        }
        
        public override void Down()
        {
            RenameIndex(table: "dbo.PatientVisitProcedures", name: "IX_Procedure_Id", newName: "IX_ProcedureId");
            RenameIndex(table: "dbo.PatientVisitProcedures", name: "IX_PatientVisit_Id", newName: "IX_PatientVisitId");
            RenameColumn(table: "dbo.PatientVisitProcedures", name: "Procedure_Id", newName: "ProcedureId");
            RenameColumn(table: "dbo.PatientVisitProcedures", name: "PatientVisit_Id", newName: "PatientVisitId");
            RenameTable(name: "dbo.PatientVisitProcedures", newName: "PatientVisitProcedure");
        }
    }
}
