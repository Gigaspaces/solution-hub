using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MasterProject
{
    class GridAPI
    {
        public static GridService createService(String serviceID, String service, Dictionary<String, String> paramters)
        {
            return new GridService(serviceID);
        }
        public static GridService createService(String serviceID)
        {
            return new GridService(serviceID);
        }
    }
}
