/**
 * 
 */

		
		c3LineChart = c3.generate({
			bindto : '#ui-historyDataLineChart',
			data : {
				x : 'time',
				xFormat : '%Y',
				columns : cdata,
				type : 'spline',
		        axes: {
		        	CJY_XT31101_8: 'y',
		        }
			},
			grid : {
				x : {
					show : true
				},
				y : {
					show : true
				}
			},
			color : {
				pattern : [ 'rgba(88,216,163,1)', 'rgba(237,28,36,0.6)',
						'rgba(4,189,254,0.6)' ]
			},
			padding : {
				top : 10,
				right : 20,
				bottom : 30,
				left : 50,
			},
			axis : {
				x : {
					type : 'timeseries',
					// if true, treat x value as localtime (Default)
					// if false, convert to UTC internally
					localtime : true,
					tick : {
						format : '%Y-%m-%d %H:%M:%S'
					}
				},
				y : {
					show: true
//					,
//					label: 'Y2 Axis Label'
				}
			}
		});
