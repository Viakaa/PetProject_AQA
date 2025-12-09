/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 99.99830920597091, "KoPercent": 0.0016907940290816572};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.9540039612888681, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.991392042571902, 500, 1500, "GET Watch Token"], "isController": false}, {"data": [0.9907447072351363, 500, 1500, "GET User's Pages"], "isController": false}, {"data": [0.9913283716734326, 500, 1500, "POST new message on discussion page"], "isController": false}, {"data": [0.45563153388608507, 500, 1500, "GET Open Main Page"], "isController": false}, {"data": [0.9916614985623273, 500, 1500, "POST remove page from watchlist"], "isController": false}, {"data": [1.0, 500, 1500, "Debug Sampler"], "isController": false}, {"data": [0.9900753853781664, 500, 1500, "POST Login"], "isController": false}, {"data": [0.991439745728326, 500, 1500, "GET CSRF Tokens"], "isController": false}, {"data": [0.9909200559112634, 500, 1500, "POST Edit Page"], "isController": false}, {"data": [0.9916677002176094, 500, 1500, "Add Page To Watchlist"], "isController": false}, {"data": [0.9896982204479329, 500, 1500, "POST append message"], "isController": false}, {"data": [0.9917323234031041, 500, 1500, "POST Create Page"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 1242020, 21, 0.0016907940290816572, 285.18104136808876, 0, 120848, 225.0, 371.0, 820.0, 1095.0, 29.787169544016145, 531.2340322986768, 17.165340238000567], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET Watch Token", 88697, 0, 0.0, 246.91097782337363, 183, 61255, 226.0, 297.0, 343.0, 541.9900000000016, 2.127343337749579, 2.371820663516965, 1.1853875938772964], "isController": false}, {"data": ["GET User's Pages", 88706, 1, 0.0011273194597885149, 260.7348770094465, 188, 71761, 238.0, 306.90000000000146, 348.0, 520.9900000000016, 2.1275622079176992, 5.628348584250789, 1.253035532256794], "isController": false}, {"data": ["POST new message on discussion page", 88680, 2, 0.0022552999548940008, 249.19687640956244, 188, 80382, 222.0, 285.0, 330.0, 499.9900000000016, 2.1269644770309304, 3.218472362501988, 1.6164120026025934], "isController": false}, {"data": ["GET Open Main Page", 88768, 6, 0.006759192501802451, 1156.8436711427744, 236, 120848, 872.0, 1205.0, 1410.9500000000007, 2336.9900000000016, 2.128909182745945, 490.8072989352175, 1.0974421749100183], "isController": false}, {"data": ["POST remove page from watchlist", 88685, 0, 0.0, 243.4467384563319, 186, 23707, 219.0, 284.0, 334.0, 547.0, 2.1270739420646314, 3.213295937979527, 1.4981248136022092], "isController": false}, {"data": ["Debug Sampler", 177476, 0, 0.0, 0.10227298338930396, 0, 47, 0.0, 0.0, 1.0, 1.0, 4.256523128409712, 3.83314266727454, 0.0], "isController": false}, {"data": ["POST Login", 177488, 5, 0.002817091859731362, 269.0032847291053, 188, 80505, 249.0, 332.0, 374.9500000000007, 503.0, 4.256782754542421, 6.798197618608521, 2.967282379609925], "isController": false}, {"data": ["GET CSRF Tokens", 88724, 0, 0.0, 246.18024435327754, 185, 12138, 226.0, 294.0, 340.0, 516.9800000000032, 2.1279412568869565, 2.370391490366535, 1.183553415007965], "isController": false}, {"data": ["POST Edit Page", 88712, 0, 0.0, 272.2141762106588, 206, 21676, 248.0, 314.90000000000146, 357.0, 519.9600000000064, 2.127682231838695, 3.2724842237709173, 1.674328889205586], "isController": false}, {"data": ["Add Page To Watchlist", 88691, 1, 0.0011275101194033216, 244.06380579765704, 166, 30878, 220.0, 292.0, 342.0, 533.0, 2.1272079007002054, 3.21359481578429, 1.4761260022471423], "isController": false}, {"data": ["POST append message", 88674, 4, 0.004510905113110946, 273.3612896677711, 165, 80504, 244.0, 305.0, 348.0, 493.9700000000048, 2.126833117569567, 3.25433543135864, 1.5331985634270522], "isController": false}, {"data": ["POST Create Page", 88719, 2, 0.002254308547210857, 260.9579233309699, 163, 80409, 240.0, 304.0, 345.0, 513.9900000000016, 2.1278319527213054, 3.254953855204812, 1.6813723620969112], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["503/Service Unavailable", 3, 14.285714285714286, 2.4154200415452248E-4], "isController": false}, {"data": ["Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 18, 85.71428571428571, 0.0014492520249271348], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 1242020, 21, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 18, "503/Service Unavailable", 3, "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["GET User's Pages", 88706, 1, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 1, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["POST new message on discussion page", 88680, 2, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 2, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["GET Open Main Page", 88768, 6, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 6, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST Login", 177488, 5, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 5, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Add Page To Watchlist", 88691, 1, "503/Service Unavailable", 1, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["POST append message", 88674, 4, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 3, "503/Service Unavailable", 1, "", "", "", "", "", ""], "isController": false}, {"data": ["POST Create Page", 88719, 2, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 1, "503/Service Unavailable", 1, "", "", "", "", "", ""], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
