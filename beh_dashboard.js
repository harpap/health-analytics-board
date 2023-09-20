var pivot = new WebDataRocks({
    container: "#wdr-component",
    toolbar: false,
    height: 400,
    width: "100%",
    report: {
        options: {
            grid: {
                showGrandTotals: "off",
                showTotals: "off"
            }
        },
        dataSource: {
            filename: "https://raw.githubusercontent.com/dimosntioudis/health-analytics-board/main/Data/Behavioral/fruits_activity_crc_meat.csv"
        },
        "slice": {
            "rows": [{
                "uniqueName": "Country"
            }],
            "columns": [
                {
                    "uniqueName": "Measures"
                }
            ],
            "measures": [{
                "uniqueName": "Portions (1 to 4)",
                "formula": "sum(\"Portions (1 to 4)\")",
                "individual": true,
                "caption": "Fruits & Vegetables"
            },
            {
                "uniqueName": "Activity (150 to 299 minutes)",
                "formula": "sum(\"Activity (150 to 299 minutes)\")",
                "individual": true,
                "caption": "Physical Activity"
            },
            {
                "uniqueName": "Meat Consumption",
                "formula": "sum(\"Meat Consumption\")",
                "individual": true,
                "caption": "Meat Consumption"
            },
            {
                "uniqueName": "CRC Incidents",
                "formula": "sum(\"CRC Incidents\")",
                "individual": true,
                "caption": "CRC Incidents"
            },
            ]
        }
    }
});

fetch('https://raw.githubusercontent.com/dimosntioudis/health-analytics-board/main/Data/Behavioral/fruits_activity_crc_meat.json')
    .then(response => response.json())
    .then(data => {
        console.log(data);
        // Create the chart using the fetched JSON data
        createMapChart("Meat Consumption", data);
        // Define an array of countries to include in the chart
        var countriesToInclude = ["Hungary", "Slovakia", "Norway", "Denmark", "Portugal", "Turkey", "Austria", "Cyprus", "Germany", "Finland"]; // Add more countries as needed

        // Filter the chartData based on the selected countries
        var filteredChartData = data.filter(function (data) {
            console.log(countriesToInclude.includes(data['Country']));
            return countriesToInclude.includes(data["Country"]);
        });

        createBubbleChart("", filteredChartData);
        createBubble("", filteredChartData);
        // createBubbleChart2("", filteredChartData);
    })
    .catch(error => {
        console.error('Error fetching JSON data:', error);
    });

// Function to create the map chart
function createMapChart(selectedValue, chartData) {
    FusionCharts.ready(function () {
        worldMap = new FusionCharts({
            type: 'maps/europe',
            renderAt: 'mapContainer',
            width: '100%',
            height: '802',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "caption": "Annual Meat Consumption in Europe",
                    "subcaption": "in kg per capita",
                    // "theme": "fusion",
                    "formatNumberScale": "0",
                    "showlabels": "1",
                },
                "colorrange": {
                    "minvalue": "0",
                    "startlabel": "Low",
                    "endlabel": "High",
                    "code": "#ff9989", // Lightest blue
                    "color": [
                        {
                            "maxvalue": "45", // Range 1: 0 to 20
                            "code": "#f2a896", // Light blue
                            "label": "39 - 45"
                        },
                        {
                            "maxvalue": "65", // Range 2: 21 to 40
                            "code": "#ea7456", // Medium-light blue
                            "label": "45 - 65"
                        },
                        {
                            "maxvalue": "75", // Range 3: 41 to 60
                            "code": "#ff5b42", // Medium blue
                            "label": "65 - 75"
                        },
                        {
                            "maxvalue": "85", // Range 3: 41 to 60
                            "code": "#e31220", // Medium blue
                            "label": "75 - 85"
                        },
                        {
                            "maxvalue": "100", // Range 4: 61 to 80
                            "code": "#990025", // Medium-dark blue
                            "label": "85 - 100"
                        }
                    ]
                },
                data: chartData.map(function (data) {
                    return {
                        id: data.id, // Use the 'id' property for country identification
                        value: data["Meat Consumption"], // Use the 'PM2.5' property for the chart value
                    };
                }),
            }
        }).render();
    });
}

function createBubbleChart() {
    FusionCharts.ready(function () {
        var salesHMChart = new FusionCharts({
            type: 'heatmap',
            renderAt: 'heatmap-container',
            width: '100%',
            height: '180',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "caption": "CRC Incidents Correlation with Risk Factors",
                    // "subcaption": "By Urbanisation",
                    "showplotborder": "1",
                    "xAxisLabelsOnTop": "1",
                    "plottooltext": "<div id='nameDiv' style='font-size: 12px; border-bottom: 1px dashed #666666; font-weight:bold; padding-bottom: 3px; margin-bottom: 5px; display: inline-block; color: #888888;' >$rowLabel :</div>{br}Rating : <b>$dataValue</b>{br}$columnLabel : <b>$tlLabel</b>{br}<b>$trLabel</b>",

                    //Cosmetics
                    "showValues": "1",
                    "showBorder": "0",
                    "bgColor": "#ffffff",
                    "showShadow": "0",
                    "usePlotGradientColor": "0",
                    "toolTipColor": "#ffffff",
                    "toolTipBorderThickness": "0",
                    "toolTipBgColor": "#000000",
                    "toolTipBgAlpha": "80",
                    "toolTipBorderRadius": "2",
                    "toolTipPadding": "5",
                    "theme": "fusion"
                },
                "dataset": [{
                    "data": [
                        {
                            "rowid": "CRC",
                            "columnid": "Fruits & Vegetables",
                            "value": "0.02"
                        },
                        {
                            "rowid": "CRC",
                            "columnid": "Physical Activity",
                            "value": "0.13"
                        },
                        {
                            "rowid": "CRC",
                            "columnid": "Meat Consumption",
                            "value": "0.05"
                        }                        
                    ]
                }],
                "colorrange": {
                    "gradient": "0",
                    "minvalue": "0",
                    "code": "E24B1A",
                    "startlabel": "Low",
                    "endlabel": "High",
                    "color": [{
                        "code": "89CFF0",
                        "minvalue": "0.00",
                        "maxvalue": "0.10"
                    },
                    {
                        "code": "0096FF",
                        "minvalue": "0.11",
                        "maxvalue": "0.20"
                    }
                    ]
                }
            }
        });
        salesHMChart.render();
    });
}

// Function to create the map chart
function createBubble(selectedValue, filteredChartData) {
    FusionCharts.ready(function () {
        var conversionChart = new FusionCharts({
            type: 'bubble',
            renderAt: 'bubble-container-2',
            width: '100%',
            height: '400',
            dataFormat: 'json',
            dataSource: {               
                "chart": {
                    "theme": "fusion",
                    "caption": "CRC Incidents Rate Analysis",
                    "subcaption": "Compared to Physical Activity and Fruit and Vegetable Consumption",
                    "xAxisMinValue": "-5",
                    "xAxisMaxValue": "100",
                    "yAxisMinValue": "0",
                    "yAxisMaxValue": "100",
                    "xAxisName": "Physical Activity - 150 to 299 minutes (%)",
                    "yAxisName": "Fruits & Vegetables - 1 to 4 portions (%)",
                    "numDivlines": "2",
                    "showValues": "1",
                    "showTrendlineLabels": "0",
                    "plotTooltext": "CRC Incidents per 100000: $zvalue%",
                    "drawQuadrant": "1",
                    "quadrantLineAlpha": "40",
                    "quadrantLineThickness": "0",
                    "quadrantXVal": "50",
                    "quadrantYVal": "20",
                    //Quadrant Labels
                    "quadrantLabelTL": "Low Activity / High Fruit & Veg.",
                    "quadrantLabelTR": "High Activity / High Fruit & Veg.",
                    "quadrantLabelBL": "Low Activity / Low Fruit & Veg.",
                    "quadrantLabelBR": "High Activity / Low Fruit & Veg.",
                    //Cosmetics
                    "yAxisLineThickness": "1",
                    "yAxisLineColor": "#999999",
                    "xAxisLineThickness": "1",
                    "xAxisLineColor": "#999999",
                    "theme": "fusion"

                },
                "categories": [{
                    "category": [{
                        "label": "0",
                        "x": "0"
                    },
                    {
                        "label": "20",
                        "x": "20",
                        "showverticalline": "1"
                    },
                    {
                        "label": "40",
                        "x": "40",
                        "showverticalline": "1"
                    },
                    {
                        "label": "60",
                        "x": "60",
                        "showverticalline": "1"
                    },
                    {
                        "label": "80",
                        "x": "80",
                        "showverticalline": "1"
                    }, {
                        "label": "100",
                        "x": "100",
                        "showverticalline": "1"
                    }
                    ]
                }],
                "dataset": [{
                    "seriesname": "High Rate",
                    "color": "#00aee4",
                    "data": [
                        {
                            "x": "14.7",
                            "y": "67.5",
                            "z": "364.92",
                            "name": "Belgium"
                        }
                    ]
                },
                {
                    "color": "#00aee4",
                    "data": [
                        {
                            "x": "4.3",
                            "y": "24",
                            "z": "331.25",
                            "name": "Romania"
                        }
                    ]
                },
                {
                    "color": "#00aee4",
                    "data": [
                        {
                            "x": "25.2",
                            "y": "62.2",
                            "z": "427.91",
                            "name": "Norway"
                        }
                    ]
                },
                {
                    "color": "#FFA500",
                    "seriesname": "Low Rate",
                    "data": [
                        {
                            "x": "19.2",
                            "y": "58.7",
                            "z": "220.52",
                            "name": "Austria"
                        }
                    ]
                }
            ],
            }
        }).render();
    });
}

// // Function to create the map chart
// function createBubbleChart(selectedValue, filteredChartData) {
//     FusionCharts.ready(function () {
//         var conversionChart = new FusionCharts({
//             type: 'bubble',
//             renderAt: 'bubble-container',
//             width: '100%',
//             height: '400',
//             dataFormat: 'json',
//             dataSource: {
//                 "chart": {
//                     "theme": "fusion",
//                     "caption": "CRC Incidents Analysis",
//                     "subcaption": "Compared to Physical Activity and Fruit and Vegatable Consumption",
//                     "xAxisMinValue": "0",
//                     "xAxisMaxValue": "100",
//                     "yAxisMinValue": "0",
//                     "yAxisMaxValue": "40",
//                     "xAxisName": "Physical Activity - 150 to 299 minutes (%)",
//                     "yAxisName": "Fruits & Vegetables - 1 to 4 portions (%)",
//                     "numDivlines": "2",
//                     "showValues": "1",
//                     "showTrendlineLabels": "0",
//                     "plotTooltext": "CRC Incidents per 100000: $zvalue%",
//                     "drawQuadrant": "1",
//                     "quadrantLineAlpha": "40",
//                     "quadrantLineThickness": "0",
//                     "quadrantXVal": "50",
//                     "quadrantYVal": "20",
//                     //Quadrant Labels
//                     "quadrantLabelTL": "Low Activity / High Fruit & Veg.",
//                     "quadrantLabelTR": "High Activity / High Fruit & Veg.",
//                     "quadrantLabelBL": "Low Activity / Low Fruit & Veg.",
//                     "quadrantLabelBR": "High Activity / Low Fruit & Veg.",
//                     //Cosmetics
//                     "yAxisLineThickness": "1",
//                     "yAxisLineColor": "#999999",
//                     "xAxisLineThickness": "1",
//                     "xAxisLineColor": "#999999",
//                     "theme": "fusion"

//                 },
//                 "categories": [{
//                     "category": [{
//                         "label": "0",
//                         "x": "0"
//                     },
//                     {
//                         "label": "20",
//                         "x": "20",
//                         "showverticalline": "1"
//                     },
//                     {
//                         "label": "40",
//                         "x": "40",
//                         "showverticalline": "1"
//                     },
//                     {
//                         "label": "60",
//                         "x": "60",
//                         "showverticalline": "1"
//                     },
//                     {
//                         "label": "80",
//                         "x": "80",
//                         "showverticalline": "1"
//                     }, {
//                         "label": "100",
//                         "x": "100",
//                         "showverticalline": "1"
//                     }
//                     ]
//                 }],
//                 "dataset": [{
//                     "color": "#FFA500",
//                     "data": [
//                         {
//                             "x": "14.5",
//                             "y": "55.5",
//                             "z": "490.07",
//                             "name": "Hungary"
//                         },
//                         {
//                             "x": "14.5",
//                             "y": "50.2",
//                             "z": "471.89",
//                             "name": "Slovakia"
//                         },
//                         {
//                             "x": "25.2",
//                             "y": "62.2",
//                             "z": "427.91",
//                             "name": "Norway"
//                         },
//                         {
//                             "x": "24",
//                             "y": "38.5",
//                             "z": "425.67",
//                             "name": "Denmark"
//                         },
//                         {
//                             "x": "9.1",
//                             "y": "58.2",
//                             "z": "422.37",
//                             "name": "Portugal"
//                         }
//                     ]
//                 },
//                 {
//                     "color": "#00aee4",
//                     "data": [
//                         {
//                             "x": "19.2",
//                             "y": "58.7",
//                             "z": "220.52",
//                             "name": "Austria"
//                         },
//                         {
//                             "x": "13.7",
//                             "y": "58.8",
//                             "z": "259.86",
//                             "name": "Cyprus"
//                         },
//                         {
//                             "x": "20.15",
//                             "y": "55.9",
//                             "z": "264.93",
//                             "name": "Germany"
//                         },
//                         {
//                             "x": "6.2",
//                             "y": "52.1",
//                             "z": "267.83",
//                             "name": "Malta"
//                         },
//                         {
//                             "x": "21.5",
//                             "y": "38.3",
//                             "z": "275.23",
//                             "name": "Luxembourg"
//                         }
//                     ]
//                 }],
//             }
//         }).render();
//     });
// }

// // Function to create the map chart
// function createBubbleChart2(selectedValue, filteredChartData) {
//     FusionCharts.ready(function () {
//         var conversionChart = new FusionCharts({
//             type: 'bubble',
//             renderAt: 'bubble-container-2',
//             width: '100%',
//             height: '400',
//             dataFormat: 'json',
//             dataSource: {
//                 "chart": {
//                     "theme": "fusion",
//                     "caption": "CRC Incidents Analysis",
//                     "subcaption": "Compared to Physical Activity and Meat Consumption",
//                     "xAxisMinValue": "0",
//                     "xAxisMaxValue": "100",
//                     "yAxisMinValue": "0",
//                     "yAxisMaxValue": "100",
//                     "xAxisName": "Physical Activity - 150 to 299 minutes (%)",
//                     "yAxisName": "Meat Consumption (kg)",
//                     "numDivlines": "2",
//                     "showValues": "1",
//                     "showTrendlineLabels": "0",
//                     "plotTooltext": "CRC Incidents per 100000: $zvalue%",
//                     "drawQuadrant": "1",
//                     "quadrantLineAlpha": "40",
//                     "quadrantLineThickness": "0",
//                     "quadrantXVal": "50",
//                     "quadrantYVal": "20",
//                     //Quadrant Labels
//                     "quadrantLabelTL": "Low Activity / High Meat",
//                     "quadrantLabelTR": "High Activity / High Meat",
//                     "quadrantLabelBL": "Low Activity / Low Meat",
//                     "quadrantLabelBR": "High Activity / Low Meat",
//                     //Cosmetics
//                     "yAxisLineThickness": "1",
//                     "yAxisLineColor": "#999999",
//                     "xAxisLineThickness": "1",
//                     "xAxisLineColor": "#999999",
//                     "theme": "fusion"

//                 },
//                 "categories": [{
//                     "category": [{
//                         "label": "0",
//                         "x": "0"
//                     },
//                     {
//                         "label": "20",
//                         "x": "20",
//                         "showverticalline": "1"
//                     },
//                     {
//                         "label": "40",
//                         "x": "40",
//                         "showverticalline": "1"
//                     },
//                     {
//                         "label": "60",
//                         "x": "60",
//                         "showverticalline": "1"
//                     },
//                     {
//                         "label": "80",
//                         "x": "80",
//                         "showverticalline": "1"
//                     }, {
//                         "label": "100",
//                         "x": "100",
//                         "showverticalline": "1"
//                     }
//                     ]
//                 }],
//                 "dataset": [{
//                     "color": "#FFA500",
//                     "data": [
//                         {
//                             "x": "14.5",
//                             "y": "84",
//                             "z": "490.07",
//                             "name": "Hungary"
//                         },
//                         {
//                             "x": "14.5",
//                             "y": "57.0",
//                             "z": "471.89",
//                             "name": "Slovakia"
//                         },
//                         {
//                             "x": "25.2",
//                             "y": "68.0",
//                             "z": "427.91",
//                             "name": "Norway"
//                         },
//                         {
//                             "x": "24.0",
//                             "y": "79.0",
//                             "z": "425.67",
//                             "name": "Denmark"
//                         },
//                         {
//                             "x": "9.1",
//                             "y": "95.0",
//                             "z": "422.37",
//                             "name": "Portugal"
//                         }
//                     ]
//                 },
//                 {
//                     "color": "#00aee4",
//                     "data": [
//                         {
//                             "x": "19.2",
//                             "y": "87.0",
//                             "z": "220.52",
//                             "name": "Austria"
//                         },
//                         {
//                             "x": "13.7",
//                             "y": "77.0",
//                             "z": "259.86",
//                             "name": "Cyprus"
//                         },
//                         {
//                             "x": "20.15",
//                             "y": "79.0",
//                             "z": "264.93",
//                             "name": "Germany"
//                         },
//                         {
//                             "x": "6.2",
//                             "y": "78.0",
//                             "z": "267.83",
//                             "name": "Malta"
//                         },
//                         {
//                             "x": "21.5",
//                             "y": "82.0",
//                             "z": "275.23",
//                             "name": "Luxembourg"
//                         }
//                     ]
//                 }],
//             }
//         }).render();
//     });
// }