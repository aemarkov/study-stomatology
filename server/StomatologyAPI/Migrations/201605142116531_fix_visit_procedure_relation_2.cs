namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class fix_visit_procedure_relation_2 : DbMigration
    {
        public override void Up()
        {
            RenameTable(name: "dbo.PatientVisitProcedures", newName: "PatientVisitProcedure");
            RenameColumn(table: "dbo.PatientVisitProcedure", name: "PatientVisit_Id", newName: "PatientVisitId");
            RenameColumn(table: "dbo.PatientVisitProcedure", name: "Procedure_Id", newName: "ProcedureId");
            RenameIndex(table: "dbo.PatientVisitProcedure", name: "IX_PatientVisit_Id", newName: "IX_PatientVisitId");
            RenameIndex(table: "dbo.PatientVisitProcedure", name: "IX_Procedure_Id", newName: "IX_ProcedureId");
        }
        
        public override void Down()
        {
            RenameIndex(table: "dbo.PatientVisitProcedure", name: "IX_ProcedureId", newName: "IX_Procedure_Id");
            RenameIndex(table: "dbo.PatientVisitProcedure", name: "IX_PatientVisitId", newName: "IX_PatientVisit_Id");
            RenameColumn(table: "dbo.PatientVisitProcedure", name: "ProcedureId", newName: "Procedure_Id");
            RenameColumn(table: "dbo.PatientVisitProcedure", name: "PatientVisitId", newName: "PatientVisit_Id");
            RenameTable(name: "dbo.PatientVisitProcedure", newName: "PatientVisitProcedures");
        }
    }
}
