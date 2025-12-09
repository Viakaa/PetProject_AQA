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

    var data = {"OkPercent": 93.33609043286462, "KoPercent": 6.663909567135374};
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
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.8565584505100634, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.9999225286643941, 500, 1500, "POST login page-2"], "isController": false}, {"data": [0.9999225286643941, 500, 1500, "POST login page-1"], "isController": false}, {"data": [1.0, 500, 1500, "POST login page-0"], "isController": false}, {"data": [1.5511090429657206E-4, 500, 1500, "POST project create"], "isController": false}, {"data": [0.9998449131513648, 500, 1500, "GET project create"], "isController": false}, {"data": [0.9573907654167958, 500, 1500, "POST login page-3"], "isController": false}, {"data": [1.0, 500, 1500, "POST project create page"], "isController": false}, {"data": [1.0, 500, 1500, "POST project edit-0"], "isController": false}, {"data": [0.8915877696725129, 500, 1500, "GET login page-2"], "isController": false}, {"data": [0.9998448650325783, 500, 1500, "POST project edit-1"], "isController": false}, {"data": [0.9998447927983859, 500, 1500, "GET login page-1"], "isController": false}, {"data": [0.49852598913886736, 500, 1500, "POST project edit"], "isController": false}, {"data": [0.9999223963991929, 500, 1500, "GET login page-0"], "isController": false}, {"data": [1.0, 500, 1500, "GET project edit page"], "isController": false}, {"data": [0.5036400247831475, 500, 1500, "POST login password"], "isController": false}, {"data": [0.5035614741406008, 500, 1500, "GET login page"], "isController": false}, {"data": [0.5023241400681748, 500, 1500, "POST login page"], "isController": false}, {"data": [1.0, 500, 1500, "POST project create-0"], "isController": false}, {"data": [1.0, 500, 1500, "POST project create-1"], "isController": false}, {"data": [1.0, 500, 1500, "GET  manager page"], "isController": false}, {"data": [1.0, 500, 1500, "Debug Sampler"], "isController": false}, {"data": [0.999844744604875, 500, 1500, "POST login password-0"], "isController": false}, {"data": [0.9999223723024375, 500, 1500, "POST login password-1"], "isController": false}, {"data": [0.8962117683589504, 500, 1500, "POST login password-2"], "isController": false}, {"data": [1.0, 500, 1500, "GET project manager page"], "isController": false}]}, function(index, item){
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
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 145080, 9668, 6.663909567135374, 305.56868624207704, 0, 1473, 216.0, 813.0, 869.0, 1044.0, 80.5800788138488, 776.6622339156083, 32.23316514285357], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["POST login page-2", 6454, 0, 0.0, 201.17508521846963, 106, 542, 200.0, 236.0, 244.0, 259.0, 3.5862170133602422, 23.791901304851784, 0.9858594621688557], "isController": false}, {"data": ["POST login page-1", 6454, 0, 0.0, 180.8281685776261, 103, 536, 180.0, 197.0, 203.0, 214.0, 3.5862209987853304, 2.222116970246146, 1.0874234571512158], "isController": false}, {"data": ["POST login page-0", 6454, 0, 0.0, 210.7767276107842, 112, 334, 210.0, 230.0, 238.0, 254.0, 3.5861213658154956, 3.024029642037966, 1.525991635624732], "isController": false}, {"data": ["POST project create", 6447, 6446, 99.98448890957035, 215.68745152784237, 111, 346, 215.0, 236.0, 243.0, 258.5199999999995, 3.5843389905140386, 35.81616040264464, 1.7730813707705968], "isController": false}, {"data": ["GET project create", 6448, 0, 0.0, 215.92617866005003, 110, 625, 216.0, 236.0, 242.0, 258.0, 3.584348935924204, 36.64226625158705, 1.0466018865638056], "isController": false}, {"data": ["POST login page-3", 3227, 0, 0.0, 459.1050511310819, 176, 701, 459.0, 496.0, 511.0, 539.0, 1.7954400163353328, 28.884839346508798, 0.49970742642145494], "isController": false}, {"data": ["POST project create page", 6447, 0, 0.0, 215.78641228478338, 110, 391, 215.0, 236.0, 242.0, 259.0, 3.584522336127782, 36.642640320816696, 1.5174779127857971], "isController": false}, {"data": ["POST project edit-0", 3223, 0, 0.0, 204.0406453614645, 112, 314, 204.0, 222.0, 228.0, 242.0, 1.7924416024416805, 1.123776864030819, 0.9487337387923738], "isController": false}, {"data": ["GET login page-2", 6443, 0, 0.0, 474.52460034145724, 177, 946, 474.0, 519.0, 533.0, 572.0, 3.583523688910321, 60.28514585839658, 0.9973674329486732], "isController": false}, {"data": ["POST project edit-1", 3223, 0, 0.0, 222.54762643499848, 112, 605, 222.0, 243.0, 251.0, 265.75999999999976, 1.792339929529843, 30.56079606405377, 0.5058459371426999], "isController": false}, {"data": ["GET login page-1", 6443, 0, 0.0, 181.02266025143587, 104, 540, 180.0, 198.0, 204.0, 216.0, 3.5838167008194444, 2.2328857960183646, 0.9729502371365287], "isController": false}, {"data": ["POST project edit", 6445, 3222, 49.9922420480993, 318.44204809930096, 112, 825, 265.0, 445.0, 456.0, 476.0, 3.5838965435556034, 49.25626609312737, 2.359017162075257], "isController": false}, {"data": ["GET login page-0", 6443, 0, 0.0, 189.16498525531583, 102, 586, 188.0, 206.0, 212.0, 225.0, 3.5839741896007453, 17.450874325536443, 1.0009927912361458], "isController": false}, {"data": ["GET project edit page", 6446, 0, 0.0, 232.93965249767314, 111, 393, 232.0, 259.0, 267.0, 282.0, 3.5841197805491163, 58.23199504643203, 1.0745359107700965], "isController": false}, {"data": ["POST login password", 6456, 0, 0.0, 841.7865551425009, 97, 1283, 842.0, 893.0, 913.0, 965.0, 3.586604897360101, 81.26968650991289, 3.423497354871944], "isController": false}, {"data": ["GET login page", 6458, 0, 0.0, 843.3013316816366, 128, 1376, 844.0, 895.0, 913.0499999999993, 966.4099999999999, 3.5877339276964393, 79.91558087119908, 2.968728363979718], "isController": false}, {"data": ["POST login page", 6454, 0, 0.0, 822.5859931825216, 354, 1473, 700.0, 1063.0, 1084.0, 1128.4499999999998, 3.585216951732336, 57.86931429566374, 4.097229684383975], "isController": false}, {"data": ["POST project create-0", 1, 0, 0.0, 143.0, 143, 143, 143.0, 143.0, 143.0, 143.0, 6.993006993006993, 4.3842875874125875, 3.5443072552447554], "isController": false}, {"data": ["POST project create-1", 1, 0, 0.0, 119.0, 119, 119, 119.0, 119.0, 119.0, 119.0, 8.403361344537815, 143.28387605042016, 2.371651785714286], "isController": false}, {"data": ["GET  manager page", 6449, 0, 0.0, 215.5050395410138, 106, 367, 215.0, 236.0, 242.0, 259.0, 3.5845361919208143, 35.268815527804755, 1.0256534221023423], "isController": false}, {"data": ["Debug Sampler", 12892, 0, 0.0, 0.07035370772572155, 0, 3, 0.0, 0.0, 1.0, 1.0, 7.167936660615546, 4.962603758934206, 0.0], "isController": false}, {"data": ["POST login password-0", 6441, 0, 0.0, 189.88029809035837, 106, 550, 189.0, 207.0, 213.0, 224.57999999999993, 3.58305101717261, 18.824990230191975, 1.4556095864763605], "isController": false}, {"data": ["POST login password-1", 6441, 0, 0.0, 180.60223567768952, 105, 537, 180.0, 197.0, 203.0, 215.0, 3.5830629764622635, 2.2324069551321633, 0.9727456127504973], "isController": false}, {"data": ["POST login password-2", 6441, 0, 0.0, 472.75500698649194, 175, 872, 473.0, 518.0, 531.0, 572.0, 3.5829254494510465, 60.27507264692859, 0.9972009307554182], "isController": false}, {"data": ["GET project manager page", 6449, 0, 0.0, 227.54938750193818, 113, 376, 227.0, 249.0, 257.0, 274.0, 3.5844764213263285, 50.54816753960349, 1.022135854518836], "isController": false}]}, function(index, item){
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
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["400/Bad Request", 3223, 33.33678113363674, 2.2215329473393988], "isController": false}, {"data": ["403/Forbidden", 6445, 66.66321886636327, 4.442376619795975], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 145080, 9668, "403/Forbidden", 6445, "400/Bad Request", 3223, "", "", "", "", "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST project create", 6447, 6446, "400/Bad Request", 3223, "403/Forbidden", 3223, "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["POST project edit", 6445, 3222, "403/Forbidden", 3222, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
