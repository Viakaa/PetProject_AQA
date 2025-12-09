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

    var data = {"OkPercent": 99.99612535101261, "KoPercent": 0.0038746489873910765};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.9507787024820186, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.990005711022273, 500, 1500, "GET Watch Token"], "isController": false}, {"data": [0.9892796985182859, 500, 1500, "GET User's Pages"], "isController": false}, {"data": [0.9893194733986349, 500, 1500, "POST new message on discussion page"], "isController": false}, {"data": [0.4205028682325409, 500, 1500, "GET Open Main Page"], "isController": false}, {"data": [0.9906045634977296, 500, 1500, "POST remove page from watchlist"], "isController": false}, {"data": [1.0, 500, 1500, "Debug Sampler"], "isController": false}, {"data": [0.9905517654782634, 500, 1500, "POST Login"], "isController": false}, {"data": [0.990593508236033, 500, 1500, "GET CSRF Tokens"], "isController": false}, {"data": [0.9902075541724955, 500, 1500, "POST Edit Page"], "isController": false}, {"data": [0.9904763264606774, 500, 1500, "Add Page To Watchlist"], "isController": false}, {"data": [0.9879765814650864, 500, 1500, "POST append message"], "isController": false}, {"data": [0.991035742834304, 500, 1500, "POST Create Page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 490367, 19, 0.0038746489873910765, 303.04275369263837, 0, 120848, 228.0, 328.0, 870.0, 1116.9900000000016, 12.460493271045985, 222.2507875141165, 7.994575397111568], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["GET Watch Token", 35020, 0, 0.0, 247.3846087949754, 183, 61255, 221.0, 265.0, 310.0, 588.0, 0.8899515222923199, 0.9982954903758681, 0.5625739347731988], "isController": false}, {"data": ["GET User's Pages", 35027, 1, 0.002854940474491107, 264.99500385416803, 188, 71761, 233.0, 283.0, 330.0, 654.9800000000032, 0.8901095279176918, 2.218175307804662, 0.5909147917737839], "isController": false}, {"data": ["POST new message on discussion page", 35017, 2, 0.005711511551532113, 256.57592026729844, 188, 80382, 221.0, 266.0, 324.0, 712.9900000000016, 0.8898749224723443, 1.3466763211670587, 0.7452844875912422], "isController": false}, {"data": ["GET Open Main Page", 35039, 6, 0.017123776363480694, 1379.8790205199891, 236, 120848, 964.0, 1527.0, 2191.0, 8802.080000000147, 0.890362750082756, 205.42225657382613, 0.5255971812382433], "isController": false}, {"data": ["POST remove page from watchlist", 35017, 0, 0.0, 246.85621269668948, 186, 23707, 218.0, 263.0, 310.0, 679.9600000000064, 0.8898749903146013, 1.346381895117596, 0.6957910632044912], "isController": false}, {"data": ["Debug Sampler", 70064, 0, 0.0, 0.11124115094770536, 0, 47, 0.0, 1.0, 1.0, 1.0, 1.7804336783118406, 1.6313896000456518, 0.0], "isController": false}, {"data": ["POST Login", 70066, 5, 0.007136128792852453, 267.39915222789494, 188, 80505, 240.0, 294.0, 332.0, 609.0, 1.7804816962029535, 2.8504628761888897, 1.3743402695878653], "isController": false}, {"data": ["GET CSRF Tokens", 35029, 0, 0.0, 243.92480516143948, 185, 12138, 221.5, 265.0, 305.0, 559.9800000000032, 0.8901474584192324, 0.9976233638674106, 0.5617429728770832], "isController": false}, {"data": ["POST Edit Page", 35027, 0, 0.0, 275.0338024952182, 206, 21676, 246.0, 293.0, 336.0, 641.9900000000016, 0.8901094148199571, 1.36984485556903, 0.7694216232193556], "isController": false}, {"data": ["Add Page To Watchlist", 35018, 1, 0.002855674224684448, 247.20446627448698, 166, 30878, 218.0, 264.0, 311.0, 649.0, 0.8899004708152392, 1.3465224126273416, 0.6866081876237151], "isController": false}, {"data": ["POST append message", 35015, 3, 0.00856775667571041, 281.46034556618696, 205, 80504, 243.0, 294.0, 351.0, 794.950000000008, 0.8898321926166622, 1.3626885407005533, 0.7104512937220439], "isController": false}, {"data": ["POST Create Page", 35028, 1, 0.0028548589699668838, 263.93568002740886, 191, 80409, 237.0, 285.0, 328.9500000000007, 619.9900000000016, 0.890135075736066, 1.3621381750800883, 0.7723477042301314], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["503/Service Unavailable", 1, 5.2631578947368425, 2.0392889407321456E-4], "isController": false}, {"data": ["Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 18, 94.73684210526316, 0.003670720093317862], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 490367, 19, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 18, "503/Service Unavailable", 1, "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": ["GET User's Pages", 35027, 1, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 1, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["POST new message on discussion page", 35017, 2, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 2, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["GET Open Main Page", 35039, 6, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 6, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST Login", 70066, 5, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 5, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["Add Page To Watchlist", 35018, 1, "503/Service Unavailable", 1, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["POST append message", 35015, 3, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 3, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["POST Create Page", 35028, 1, "Non HTTP response code: java.net.SocketException/Non HTTP response message: Connection reset", 1, "", "", "", "", "", "", "", ""], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
