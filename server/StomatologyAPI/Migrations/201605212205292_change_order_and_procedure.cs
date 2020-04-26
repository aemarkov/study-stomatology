namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class change_order_and_procedure : DbMigration
    {
        public override void Up()
        {
            AddColumn("dbo.Orders", "FinishDate", c => c.DateTime());
            AddColumn("dbo.Orders", "TotalCose", c => c.Decimal(precision: 18, scale: 2));
            AddColumn("dbo.Orders", "Discriminator", c => c.String(nullable: false, maxLength: 128));
            AlterColumn("dbo.Procedures", "Cost", c => c.Decimal(nullable: false, precision: 18, scale: 2));
            AlterColumn("dbo.TechnicanProcedures", "Cost", c => c.Decimal(nullable: false, precision: 18, scale: 2));
        }
        
        public override void Down()
        {
            AlterColumn("dbo.TechnicanProcedures", "Cost", c => c.Int(nullable: false));
            AlterColumn("dbo.Procedures", "Cost", c => c.Int(nullable: false));
            DropColumn("dbo.Orders", "Discriminator");
            DropColumn("dbo.Orders", "TotalCose");
            DropColumn("dbo.Orders", "FinishDate");
        }
    }
}
