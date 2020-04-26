namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class fix_visit_procedure_relation : DbMigration
    {
        public override void Up()
        {
            DropForeignKey("dbo.Procedures", "PatientVisit_Id", "dbo.PatientVisits");
            DropIndex("dbo.Procedures", new[] { "PatientVisit_Id" });
            CreateTable(
                "dbo.PatientVisitProcedures",
                c => new
                    {
                        PatientVisit_Id = c.Int(nullable: false),
                        Procedure_Id = c.Int(nullable: false),
                    })
                .PrimaryKey(t => new { t.PatientVisit_Id, t.Procedure_Id })
                .ForeignKey("dbo.PatientVisits", t => t.PatientVisit_Id, cascadeDelete: true)
                .ForeignKey("dbo.Procedures", t => t.Procedure_Id, cascadeDelete: true)
                .Index(t => t.PatientVisit_Id)
                .Index(t => t.Procedure_Id);
            
            DropColumn("dbo.Procedures", "PatientVisit_Id");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Procedures", "PatientVisit_Id", c => c.Int());
            DropForeignKey("dbo.PatientVisitProcedures", "Procedure_Id", "dbo.Procedures");
            DropForeignKey("dbo.PatientVisitProcedures", "PatientVisit_Id", "dbo.PatientVisits");
            DropIndex("dbo.PatientVisitProcedures", new[] { "Procedure_Id" });
            DropIndex("dbo.PatientVisitProcedures", new[] { "PatientVisit_Id" });
            DropTable("dbo.PatientVisitProcedures");
            CreateIndex("dbo.Procedures", "PatientVisit_Id");
            AddForeignKey("dbo.Procedures", "PatientVisit_Id", "dbo.PatientVisits", "Id");
        }
    }
}
