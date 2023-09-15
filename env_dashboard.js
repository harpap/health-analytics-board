var pivot = new WebDataRocks({
    container: "#wdr-component",
    toolbar: false,
    height: 500,
    width: "100%",
    report: {
        options: {
            grid: {
                showGrandTotals: "off"
            }
        },
        dataSource: {
            "dataSourceType": "json",
            "filename": "https://raw.githubusercontent.com/dimosntioudis/health-analytics-board/main/Data/Environmental%20Data/eu_env_pollutants_agg.json"
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
                "uniqueName": "PM2.5",
                "formula": "sum(\"PM2.5\")",
                "individual": true,
                "caption": "PM2.5 (ug/m3)"
            },
            {
                "uniqueName": "PM10",
                "formula": "sum(\"PM10\")",
                "individual": true,
                "caption": "PM10 (ug/m3)"
            },
            {
                "uniqueName": "O3",
                "formula": "sum(\"O3\")",
                "individual": true,
                "caption": "O3 (ug/m3)"
            },
            {
                "uniqueName": "NO2",
                "formula": "sum(\"NO2\")",
                "individual": true,
                "caption": "NO2 (ug/m3)"
            },
            {
                "uniqueName": "Population",
                "formula": "sum(\"Population\")",
                "individual": true,
                "caption": "Population"
            }]
        }
    },
    reportcomplete: function () {
        pivot.off("reportcomplete");
        createBubbleChart();
    }
});

fetch('https://raw.githubusercontent.com/dimosntioudis/health-analytics-board/main/Data/Environmental%20Data/eu_env_pollutants_agg.json')
    .then(response => response.json())
    .then(data => {
        console.log(data);
        // Create the chart using the fetched JSON data
        createStackChart(data);
        createMapChart("PM2.5", data);
    })
    .catch(error => {
        console.error('Error fetching JSON data:', error);
    });



// Function to create a stacked column chart
function createStackChart(chartData) {
    FusionCharts.ready(function () {
        var chart = new FusionCharts({
            type: "stackedcolumn2d",
            renderAt: "stackContainer",
            width: "100%",
            height: "500",
            dataSource: {
                data: chartData,
                chart: {
                    caption: "Pollutant Levels by Country",
                    subcaption: "Stacked Column Chart",
                    theme: "fusion"
                },
                categories: [
                    {
                        category: chartData.map(function (data) {
                            return { label: data.Country };
                        })
                    }
                ],
                dataset: [
                    {
                        seriesname: "PM2.5",
                        data: chartData.map(function (data) { return { value: data["PM2.5"] }; })
                    },
                    {
                        seriesname: "PM10",
                        data: chartData.map(function (data) { return { value: data["PM10"] }; })
                    },
                    {
                        seriesname: "NO2",
                        data: chartData.map(function (data) { return { value: data["NO2"] }; })
                    }
                ]
            }
        });

        chart.render();
    });
}

var worldMap;

// Function to create the map chart
function createMapChart(selectedValue, chartData) {
    FusionCharts.ready(function () {
        worldMap = new FusionCharts({
            type: 'maps/europe',
            renderAt: 'mapContainer',
            width: '100%',
            height: '600',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "caption": "Particulate matter 2.5 for each EU Country",
                    "subcaption": "A lighter shade indicates a better performance",
                    "theme": "fusion",
                    "formatNumberScale": "0",
                    "showlabels": "1",
                },
                "colorrange": {
                    "minvalue": "0",
                    "startlabel": "Low",
                    "endlabel": "High",
                    "code": "#fde0e0", // Lightest blue
                    "color": [
                        {
                            "maxvalue": "5", // Range 1: 0 to 20
                            "code": "#f4c1c1" // Light blue
                        },
                        {
                            "maxvalue": "10", // Range 2: 21 to 40
                            "code": "#fdaaaa" // Medium-light blue
                        },
                        {
                            "maxvalue": "15", // Range 3: 41 to 60
                            "code": "#f97c7c" // Medium blue
                        },
                        {
                            "maxvalue": "20", // Range 4: 61 to 80
                            "code": "#ee6969" // Medium-dark blue
                        }
                    ]
                },
                data: chartData.map(function (data) {
                    return {
                        id: data.id, // Use the 'id' property for country identification
                        value: data[selectedValue], // Use the 'PM2.5' property for the chart value
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
            height: '600',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "caption": "Top 10 Countries with highest PM10 Concentrations",
                    "subcaption": "By Urbanisation",
                    "xAxisName": "Urbanization",
                    "yAxisName": "Country",
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
                            "rowid": "Cyprus",
                            "columnid": "All Areas",
                            "value": "32.4"
                        },
                        {
                            "rowid": "Cyprus",
                            "columnid": "Rural",
                            "value": "28.1"
                        },
                        {
                            "rowid": "Cyprus",
                            "columnid": "Urban",
                            "value": "34.7"
                        },
                        {
                            "rowid": "Bulgaria",
                            "columnid": "All Areas",
                            "value": "26"
                        },
                        {
                            "rowid": "Bulgaria",
                            "columnid": "Rural",
                            "value": "21.6"
                        },
                        {
                            "rowid": "Bulgaria",
                            "columnid": "Urban",
                            "value": "29.7"
                        },
                        {
                            "rowid": "Malta",
                            "columnid": "All Areas",
                            "value": "25.2"
                        },
                        {
                            "rowid": "Malta",
                            "columnid": "Rural",
                            "value": "22.3"
                        },
                        {
                            "rowid": "Malta",
                            "columnid": "Urban",
                            "value": "25.5"
                        },
                        {
                            "rowid": "Italy",
                            "columnid": "All Areas",
                            "value": "23.9"
                        },
                        {
                            "rowid": "Italy",
                            "columnid": "Rural",
                            "value": "21.9"
                        },
                        {
                            "rowid": "Italy",
                            "columnid": "Urban",
                            "value": "26.6"
                        },
                        {
                            "rowid": "Greece",
                            "columnid": "All Areas",
                            "value": "23.9"
                        },
                        {
                            "rowid": "Greece",
                            "columnid": "Rural",
                            "value": "17.9"
                        },
                        {
                            "rowid": "Greece",
                            "columnid": "Urban",
                            "value": "25.3"
                        },
                        {
                            "rowid": "Romania",
                            "columnid": "All Areas",
                            "value": "23.2"
                        },
                        {
                            "rowid": "Romania",
                            "columnid": "Rural",
                            "value": "20.0"
                        },
                        {
                            "rowid": "Romania",
                            "columnid": "Urban",
                            "value": "27.7"
                        },
                        {
                            "rowid": "Croatia",
                            "columnid": "All Areas",
                            "value": "22.8"
                        },
                        {
                            "rowid": "Croatia",
                            "columnid": "Rural",
                            "value": "19.9"
                        },
                        {
                            "rowid": "Croatia",
                            "columnid": "Urban",
                            "value": "25.6"
                        },
                        {
                            "rowid": "Poland",
                            "columnid": "All Areas",
                            "value": "22.7"
                        },
                        {
                            "rowid": "Poland",
                            "columnid": "Rural",
                            "value": "19.9"
                        },
                        {
                            "rowid": "Poland",
                            "columnid": "Urban",
                            "value": "25.6"
                        },
                        {
                            "rowid": "Hungary",
                            "columnid": "All Areas",
                            "value": "21.5"
                        },
                        {
                            "rowid": "Hungary",
                            "columnid": "Rural",
                            "value": "20"
                        },
                        {
                            "rowid": "Hungary",
                            "columnid": "Urban",
                            "value": "23.5"
                        },
                        {
                            "rowid": "Slovakia",
                            "columnid": "All Areas",
                            "value": "20.1"
                        },
                        {
                            "rowid": "Slovakia",
                            "columnid": "Rural",
                            "value": "19.3"
                        },
                        {
                            "rowid": "Slovakia",
                            "columnid": "Urban",
                            "value": "21.3"
                        },
                        
                    ]
                }],
                "colorrange": {
                    "gradient": "0",
                    "minvalue": "0",
                    "code": "E24B1A",
                    "startlabel": "Low",
                    "endlabel": "High",
                    "color": [{
                        "code": "6DA81E",
                        "minvalue": "0",
                        "maxvalue": "25",
                        "label": "Good"
                    },
                    {
                        "code": "F6BC33",
                        "minvalue": "25",
                        "maxvalue": "30",
                        "label": "Fair"
                    },
                    {
                        "code": "E24B1A",
                        "minvalue": "30",
                        "maxvalue": "100",
                        "label": "Poor"
                    }
                    ]
                }
            }
        });
        salesHMChart.render();
    });
}
