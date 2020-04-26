namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class FUUUUUUCK_2 : DbMigration
    {
        public override void Up()
        {
            RenameColumn(table: "dbo.Orders", name: "DentalTechnican_Id", newName: "DentalTechnicanId");
            RenameIndex(table: "dbo.Orders", name: "IX_DentalTechnican_Id", newName: "IX_DentalTechnicanId");
            AlterColumn("dbo.PatientVisits", "DoctorId", c => c.Int());
            CreateIndex("dbo.PatientVisits", "DoctorId");
            AddForeignKey("dbo.PatientVisits", "DoctorId", "dbo.Doctors", "Id");
        }
        
        public override void Down()
        {
            DropForeignKey("dbo.PatientVisits", "DoctorId", "dbo.Doctors");
            DropIndex("dbo.PatientVisits", new[] { "DoctorId" });
            AlterColumn("dbo.PatientVisits", "DoctorId", c => c.Int(nullable: false));
            RenameIndex(table: "dbo.Orders", name: "IX_DentalTechnicanId", newName: "IX_DentalTechnican_Id");
            RenameColumn(table: "dbo.Orders", name: "DentalTechnicanId", newName: "DentalTechnican_Id");
        }
    }
}
