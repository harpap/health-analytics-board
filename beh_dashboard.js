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
                "uniqueName": "Portions (more than 5)",
                "formula": "sum(\"Portions (more than 5)\")",
                "individual": true,
                "caption": "Fruits & Vegetables"
            },
            {
                "uniqueName": "Activity (0 minutes)",
                "formula": "sum(\"Activity (0 minutes)\")",
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
            height: '801',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "caption": "Annual Meat Consumptions",
                    "subcaption": "kg per capita",
                    "theme": "fusion",
                    "formatNumberScale": "0",
                    "showlabels": "1",
                },
                "colorrange": {
                    "minvalue": "0",
                    "startlabel": "Low",
                    "endlabel": "High",
                    "code": "#ffcbd1", // Lightest blue
                    "color": [
                        {
                            "maxvalue": "45", // Range 1: 0 to 20
                            "code": "#f69697" // Light blue
                        },
                        {
                            "maxvalue": "65", // Range 2: 21 to 40
                            "code": "#f94449" // Medium-light blue
                        },
                        {
                            "maxvalue": "75", // Range 3: 41 to 60
                            "code": "#ff2c2c" // Medium blue
                        },
                        {
                            "maxvalue": "85", // Range 3: 41 to 60
                            "code": "#f01e2c" // Medium blue
                        },
                        {
                            "maxvalue": "100", // Range 4: 61 to 80
                            "code": "#c30010" // Medium-dark blue
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

// Function to create the map chart
function createBubbleChart(selectedValue, filteredChartData) {
    FusionCharts.ready(function () {
        var conversionChart = new FusionCharts({
            type: 'bubble',
            renderAt: 'bubble-container',
            width: '100%',
            height: '400',
            dataFormat: 'json',
            dataSource: {
                "chart": {
                    "theme": "fusion",
                    "caption": "CRC Incidents Analysis",
                    "subcaption": "Compared to Physical Activity and Fruit and Vegatable Consumption",
                    "xAxisMinValue": "0",
                    "xAxisMaxValue": "100",
                    "yAxisMinValue": "0",
                    "yAxisMaxValue": "40",
                    "xAxisName": "Physical Activity - No Activity (%)",
                    "yAxisName": "Fruits & Vegetables (%)",
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
                    "color": "#de0a26",
                    "data": [
                        {
                            "x": "59.4",
                            "y": "8.2",
                            "z": "490.07",
                            "name": "Hungary"
                        },
                        {
                            "x": "51.9",
                            "y": "8.5",
                            "z": "471.89",
                            "name": "Slovakia"
                        },
                        {
                            "x": "85.9",
                            "y": "8.5",
                            "z": "427.91",
                            "name": "Norway"
                        },
                        {
                            "x": "82.5",
                            "y": "22.9",
                            "z": "425.67",
                            "name": "Denmark"
                        },
                        {
                            "x": "34.4",
                            "y": "14.4",
                            "z": "422.37",
                            "name": "Portugal"
                        }
                    ]
                },
                {
                    "color": "#00aee4",
                    "data": [
                        {
                            "y": "5.6",
                            "x": "67.9",
                            "z": "220.52",
                            "name": "Austria"
                        },
                        {
                            "y": "7.9",
                            "x": "35.5",
                            "z": "259.86",
                            "name": "Cyprus"
                        },
                        {
                            "y": "10.9",
                            "x": "72.5",
                            "z": "264.93",
                            "name": "Germany"
                        },
                        {
                            "y": "11.6",
                            "x": "28.4",
                            "z": "267.83",
                            "name": "Malta"
                        },
                        {
                            "y": "13.6",
                            "x": "66.6",
                            "z": "275.23",
                            "name": "Luxamburg"
                        }
                    ]
                }],
            }
        }).render();
    });
}