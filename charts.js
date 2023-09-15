var pivot = new WebDataRocks({
    container: "#wdr-component",
    toolbar: false,
    height: 500,
    width: "100%",
    report: {
        dataSource: {
            "dataSourceType": "json",
            "filename": "https://raw.githubusercontent.com/dimosntioudis/health-analytics-board/main/Data/CRC%20Incidence%20Rates/eu_crc_incidents.json"
        },
        "slice": {
            "rows": [{
                "uniqueName": "label"
            }],
            "columns": [
                {
                    "uniqueName": "Gender"
                },
                {
                    "uniqueName": "Measures"
                }
            ],
            "measures": [{
                "uniqueName": "Value",
                "formula": "sum(\"Value\")",
                "individual": true,
                "caption": "CRC Incidents"
            }]
        }
    },
    reportcomplete: function () {
        pivot.off("reportcomplete");
        createFusionChart();
        createPieChart();
        createBarChart();
        createMapChart();
    }
});

function createFusionChart() {
    var chart = new FusionCharts({
        "type": "stackedcolumn2d",
        "renderAt": "fusionchartContainer",
        "width": "100%",
        "height": 500
    });

    pivot.fusioncharts.getData({
        type: chart.chartType()
    }, function (data) {
        chart.setJSONData(data);
        chart.setChartAttribute("theme", "fusion");
        chart.setChartAttribute("caption", "Total CRC Incidents for each Country");
        chart.render();
    });
}

function createPieChart() {
    var chart = new FusionCharts({
        type: "pie2d",
        renderAt: "fusionchartPie",
        width: "100%",
        height: 500
    });
    pivot.fusioncharts.getData({
        type: chart.chartType(),
        "slice": {
            "rows": [
                {
                    "uniqueName": "[Measures]"
                }
            ],
            "columns": [
                {
                    "uniqueName": "Gender"
                }
            ],
            "measures": [
                {
                    "uniqueName": "Value",
                    "aggregation": "sum"
                }
            ]
        }
    }, function (data) {
        chart.setJSONData(data);
        chart.setChartAttribute("theme", "fusion");
        chart.setChartAttribute("caption", "Percentage of CRC Incidents by Gender");
        chart.setChartAttribute("paletteColors", "#9d87f5, #faa27f, #9afa7f, #e37ffa, #7de1fa, #f8fa7f");
        chart.render();
    });
}

function createBarChart() {
    var chart = new FusionCharts({
        type: "bar2d",
        renderAt: "fusionchartBar",
        width: "100%",
        height: 500
    });
    pivot.fusioncharts.getData({
        type: chart.chartType(), "slice": {
            "rows": [
                {
                    "uniqueName": "[Measures]"
                }
            ],
            "columns": [
                {
                    "uniqueName": "Age Group"
                }
            ],
            "measures": [
                {
                    "uniqueName": "Value",
                    "aggregation": "sum"
                }
            ]
        }
    },
        function (data) {
            chart.setJSONData(data);
            chart.setChartAttribute("theme", "fusion");
            chart.setChartAttribute("caption", "Total CRC Incidents by Age Group");
            chart.setChartAttribute("paletteColors", "#bc5cdb, #493999, #8790a8");
            chart.render();
        }

    );
}

// Function to create the map chart
function createMapChart() {
    FusionCharts.ready(function () {
        var worldMap = new FusionCharts({
            type: 'maps/europe',
            renderAt: 'mapContainer',
            width: '100%',
            height: '1001',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "caption": "Total CRC Incident for each EU Country",
                    "subcaption": "Per 100000 people",
                    "theme": "fusion",
                    "formatNumberScale": "0",
                    "showlabels": "1",
                },
                "colorrange": {
                    "minvalue": "0",
                    "startlabel": "Low",
                    "endlabel": "High",
                    "code": "#E6F7FF", // Lightest blue
                    "color": [
                        {
                            "maxvalue": "100", // Range 1: 0 to 20
                            "code": "#3ACBE8" // Light blue
                        },
                        {
                            "maxvalue": "200", // Range 2: 21 to 40
                            "code": "#1CA3DE" // Medium-light blue
                        },
                        {
                            "maxvalue": "300", // Range 3: 41 to 60
                            "code": "#0D85D8" // Medium blue
                        },
                        {
                            "maxvalue": "400", // Range 4: 61 to 80
                            "code": "#0160C9" // Medium-dark blue
                        },
                        {
                            "maxvalue": "500",
                            "code": "#0041C7" // Darkest blue
                        }
                    ]
                },
                "data": [
                    {
                        "id": "030",
                        "value": "427.91"
                    },
                    {
                        "id": "031",
                        "value": "326.42"
                    },
                    {
                        "id": "010",
                        "value": "425.67"
                    },
                    {
                        "id": "032",
                        "value": "422.37"
                    },
                    {
                        "id": "011",
                        "value": "305.40"
                    },
                    {
                        "id": "033",
                        "value": "331.25"
                    },
                    {
                        "id": "012",
                        "value": "267.13"
                    },
                    {
                        "id": "013",
                        "value": "317.07"
                    },
                    {
                        "id": "035",
                        "value": "360.11"
                    },
                    {
                        "id": "014",
                        "value": "264.93"
                    },
                    {
                        "id": "036",
                        "value": "471.89"
                    },
                    {
                        "id": "015",
                        "value": "282.94"
                    },
                    {
                        "id": "037",
                        "value": "418.86"
                    },
                    {
                        "id": "016",
                        "value": "490.07"
                    },
                    {
                        "id": "038",
                        "value": "377.18"
                    },
                    {
                        "id": "017",
                        "value": "300.56"
                    },
                    {
                        "id": "039",
                        "value": "287.37"
                    },
                    {
                        "id": "018",
                        "value": "362.36"
                    },
                    {
                        "id": "019",
                        "value": "310.49"
                    },
                    {
                        "id": "040",
                        "value": "231.58"
                    },
                    {
                        "id": "041",
                        "value": "279.17"
                    },
                    {
                        "id": "020",
                        "value": "406.16"
                    },
                    {
                        "id": "042",
                        "value": "351.39"
                    },
                    {
                        "id": "044",
                        "value": "259.86"
                    },
                    {
                        "id": "022",
                        "value": "301.64"
                    },
                    {
                        "id": "001",
                        "value": "81.66"
                    },
                    {
                        "id": "023",
                        "value": "275.22"
                    },
                    {
                        "id": "045",
                        "value": "219.40"
                    },
                    {
                        "id": "003",
                        "value": "220.52"
                    },
                    {
                        "id": "025",
                        "value": "267.83"
                    },
                    {
                        "id": "004",
                        "value": "331.87"
                    },
                    {
                        "id": "005",
                        "value": "364.91"
                    },
                    {
                        "id": "006",
                        "value": "288.57"
                    },
                    {
                        "id": "028",
                        "value": "292.55"
                    },
                    {
                        "id": "007",
                        "value": "289.94"
                    },
                    {
                        "id": "008",
                        "value": "387.57"
                    }
                ]
            }
        }).render();
    });
}
