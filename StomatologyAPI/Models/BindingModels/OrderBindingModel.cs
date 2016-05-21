using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace StomatologyAPI.Models.BindingModels
{
	public class OrderBindingModel:Order
	{
		public decimal TotalCost { get; set; }
	}
}