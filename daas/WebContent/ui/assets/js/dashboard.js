var Dashboard = function () {

    return {
        //main function to initiate the module

        initCharts: function () {

            if (!jQuery.plot) {
                return;
            }

            var data = [];
            var totalPoints = 250;

            // random data generator for plot charts

            function getRandomData() {
                if (data.length > 0) data = data.slice(1);
                // do a random walk
                while (data.length < totalPoints) {
                    var prev = data.length > 0 ? data[data.length - 1] : 50;
                    var y = prev + Math.random() * 10 - 5;
                    if (y < 0) y = 0;
                    if (y > 100) y = 100;
                    data.push(y);
                }
                // zip the generated y values with the x values
                var res = [];
                for (var i = 0; i < data.length; ++i) res.push([i, data[i]])
                return res;
            }

            //Line Chart
            function line_chart() {
                function randValue() {
                    return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
                }
                var pageviews = [
                    [1, randValue()],
                    [2, randValue()],
                    [3, 2 + randValue()],
                    [4, 3 + randValue()],
                    [5, 5 + randValue()],
                    [6, 10 + randValue()],
                    [7, 15 + randValue()],
                    [8, 20 + randValue()],
                    [9, 25 + randValue()],
                    [10, 30 + randValue()],
                    [11, 35 + randValue()],
                    [12, 25 + randValue()],
                    [13, 15 + randValue()],
                    [14, 20 + randValue()],
                    [15, 45 + randValue()],
                    [16, 50 + randValue()],
                    [17, 65 + randValue()],
                    [18, 70 + randValue()],
                    [19, 85 + randValue()],
                    [20, 80 + randValue()],
                    [21, 75 + randValue()],
                    [22, 80 + randValue()],
                    [23, 75 + randValue()],
                    [24, 70 + randValue()],
                    [25, 65 + randValue()],
                    [26, 75 + randValue()],
                    [27, 80 + randValue()],
                    [28, 85 + randValue()],
                    [29, 90 + randValue()],
                    [30, 95 + randValue()]
                ];
                var visitors = [
                    [1, randValue() - 5],
                    [2, randValue() - 5],
                    [3, randValue() - 5],
                    [4, 6 + randValue()],
                    [5, 5 + randValue()],
                    [6, 20 + randValue()],
                    [7, 25 + randValue()],
                    [8, 36 + randValue()],
                    [9, 26 + randValue()],
                    [10, 38 + randValue()],
                    [11, 39 + randValue()],
                    [12, 50 + randValue()],
                    [13, 51 + randValue()],
                    [14, 12 + randValue()],
                    [15, 13 + randValue()],
                    [16, 14 + randValue()],
                    [17, 15 + randValue()],
                    [18, 15 + randValue()],
                    [19, 16 + randValue()],
                    [20, 17 + randValue()],
                    [21, 18 + randValue()],
                    [22, 19 + randValue()],
                    [23, 20 + randValue()],
                    [24, 21 + randValue()],
                    [25, 14 + randValue()],
                    [26, 24 + randValue()],
                    [27, 25 + randValue()],
                    [28, 26 + randValue()],
                    [29, 27 + randValue()],
                    [30, 31 + randValue()]
                ];

                var plot = $.plot($("#line_chart"), [{
                            data: pageviews,
                            label: "Unique Visits"
                        }, {
                            data: visitors,
                            label: "Page Views"
                        }
                    ], {
                        series: {
                            lines: {
                                show: true,
                                lineWidth: 2,
                                fill: true,
                                fillColor: {
                                    colors: [{
                                            opacity: 0.05
                                        }, {
                                            opacity: 0.01
                                        }
                                    ]
                                }
                            },
                            points: {
                                show: true
                            },
                            shadowSize: 2
                        },
                        grid: {
                            hoverable: true,
                            clickable: true,
                            tickColor: "#eee",
                            borderWidth: 0
                        },
                        colors: ["#d12610", "#37b7f3", "#52e136"],
                        xaxis: {
                            ticks: 11,
                            tickDecimals: 0
                        },
                        yaxis: {
                            ticks: 11,
                            tickDecimals: 0
                        }
                    });


                function showTooltip(x, y, contents) {
                    $('<div id="tooltip">' + contents + '</div>').css({
                            position: 'absolute',
                            display: 'none',
                            top: y + 5,
                            left: x + 15,
                            border: '1px solid #333',
                            padding: '4px',
                            color: '#fff',
                            'border-radius': '3px',
                            'background-color': '#333',
                            opacity: 0.80
                        }).appendTo("body").fadeIn(200);
                }

                var previousPoint = null;
                $("#line_chart").bind("plothover", function (event, pos, item) {
                    $("#x").text(pos.x.toFixed(2));
                    $("#y").text(pos.y.toFixed(2));

                    if (item) {
                        if (previousPoint != item.dataIndex) {
                            previousPoint = item.dataIndex;

                            $("#tooltip").remove();
                            var x = item.datapoint[0].toFixed(2),
                                y = item.datapoint[1].toFixed(2);

                            showTooltip(item.pageX, item.pageY, item.series.label + " of " + x + " = " + y);
                        }
                    } else {
                        $("#tooltip").remove();
                        previousPoint = null;
                    }
                });
            }

            //Dynamic Chart
            function dynamic_chart() {
                //server load
                var options = {
                    series: {
                        shadowSize: 1
                    },
                    lines: {
                        show: true,
                        lineWidth: 0.5,
                        fill: true,
                        fillColor: {
                            colors: [{
                                    opacity: 0.1
                                }, {
                                    opacity: 1
                                }
                            ]
                        }
                    },
                    yaxis: {
                        min: 0,
                        max: 100,
                        tickFormatter: function (v) {
                            return v + "%";
                        }
                    },
                    xaxis: {
                        show: false
                    },
                    colors: ["#6ef146"],
                    grid: {
                        tickColor: "#a8a3a3",
                        borderWidth: 0
                    }
                };

                var updateInterval = 30;
                var plot = $.plot($("#dynamic_chart"), [getRandomData()], options);

                function update() {
                    plot.setData([getRandomData()]);
                    plot.draw();
                    setTimeout(update, updateInterval);
                }
                update();
            }

            //bar chart
            function bar_chart() {
                var d1 = [];
                for (var i = 0; i <= 10; i += 1)
                    d1.push([i, parseInt(Math.random() * 30)]);

                var d2 = [];
                for (var i = 0; i <= 10; i += 1)
                    d2.push([i, parseInt(Math.random() * 30)]);

                var d3 = [];
                for (var i = 0; i <= 10; i += 1)
                    d3.push([i, parseInt(Math.random() * 30)]);

                var stack = 0,
                    bars = true,
                    lines = false,
                    steps = false;

                function plotWithOptions() {
                    $.plot($("#bar_chart"), [d1, d2, d3], {
                            series: {
                                stack: stack,
                                lines: {
                                    show: lines,
                                    fill: true,
                                    steps: steps
                                },
                                bars: {
                                    show: bars,
                                    barWidth: 0.6
                                }
                            }
                        });
                }

                $(".stackControls input").click(function (e) {
                    e.preventDefault();
                    stack = $(this).val() == "With stacking" ? true : null;
                    plotWithOptions();
                });
                $(".graphControls input").click(function (e) {
                    e.preventDefault();
                    bars = $(this).val().indexOf("Bars") != -1;
                    lines = $(this).val().indexOf("Lines") != -1;
                    steps = $(this).val().indexOf("steps") != -1;
                    plotWithOptions();
                });

                plotWithOptions();
            }

            //graph
            line_chart();
            dynamic_chart();
            bar_chart();

        },

        initMiniCharts: function () {

            $('#mini_chart').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: App.getLayoutColorCode('yellow')
            });

            $('.easy-pie-chart .number.visits').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: App.getLayoutColorCode('green')
            });

            $('.easy-pie-chart .number.bounce').easyPieChart({
                animate: 1000,
                size: 75,
                lineWidth: 3,
                barColor: App.getLayoutColorCode('red')
            });

        },

        initPieCharts: function () {

            var data = [];
            var series = Math.floor(Math.random() * 10) + 1;
            series = series < 5 ? 5 : series;

            for (var i = 0; i < series; i++) {
                data[i] = {
                    label: "Series" + (i + 1),
                    data: Math.floor(Math.random() * 100) + 1
                }
            }

            // GRAPH 6
            $.plot($("#pie_chart_6"), data, {
                    series: {
                        pie: {
                            show: true,
                            radius: 1,
                            label: {
                                show: true,
                                radius: 2 / 3,
                                formatter: function (label, series) {
                                    return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">' + label + '<br/>' + Math.round(series.percent) + '%</div>';
                                },
                                threshold: 0.1
                            }
                        }
                    },
                    legend: {
                        show: false
                    }
                });

            // DONUT
            $.plot($("#donut"), data, {
                    series: {
                        pie: {
                            innerRadius: 0.5,
                            show: true
                        }
                    }
            });



        },

        initSparkline: function () {
            $(".sparkline.bar_week").sparkline([5, 6, 7, 2, 0, -4, -2], {
                type: 'bar',
                height: '40',
                barWidth: 5,
                barColor: '#4d6189',
                negBarColor: '#a20051'
            });

            $(".sparkline.line_day").sparkline([5, 6, 7, 9, 9, 5, 4, 6, 6, 4, 6, 7], {
                type: 'line',
                height: '40',
                drawNormalOnTop: false
            });

            $(".sparkline.pie_week").sparkline([1, 1, 2], {
                type: 'pie',
                width: '40',
                height: '40'
            });

            $('.sparkline.stacked_month').sparkline(['0:2', '2:4', '4:2', '4:1'], {
                type: 'bar',
                height: '40',
                barWidth: 10,
                barColor: '#4d6189',
                negBarColor: '#a20051'
            });
        }

    };

}();