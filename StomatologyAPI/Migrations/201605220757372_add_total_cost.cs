namespace StomatologyAPI.Migrations
{
    using System;
    using System.Data.Entity.Migrations;
    
    public partial class add_total_cost : DbMigration
    {
        public override void Up()
        {
            DropColumn("dbo.Orders", "TotalCost");
        }
        
        public override void Down()
        {
            AddColumn("dbo.Orders", "TotalCost", c => c.Decimal(precision: 18, scale: 2));
        }
    }
}
