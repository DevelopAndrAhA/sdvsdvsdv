package controllers;

/**
 * Created by Altynbek on 29.06.2022.
 */
public class Init {
    float [] eva1 = new float[]{
            (float)0.0025661422,
            (float)-0.009281906,
            (float)0.0075907344,
            (float)0.0075853094,
            (float)0.023826191,
            (float)0.06450295,
            (float)0.066978574,
            (float)-0.09228077,
            (float)-0.061349288,
            (float)0.09880976,
            (float)0.0019061121,
            (float)0.0021235847,
            (float)0.009873625,
            (float)0.0039604288,
            (float)-0.0013777277,
            (float)-0.061259966,
            (float)-0.0060335505,
            (float)-0.0029065672,
            (float)-9.306294E-4,
            (float)-0.0051885047,
            (float)0.20298202,
            (float)0.011893955,
            (float)-0.043683797,
            (float)-0.0023777466,
            (float)0.18473122,
            (float)0.0037449768,
            (float)0.0067455973,
            (float)0.20763972,
            (float)0.08674956,
            (float)-0.021529313,
            (float)-0.0011027856,
            (float)-0.031092152,
            (float)0.049864743,
            (float)0.0014227153,
            (float)-0.0387269,
            (float)-0.12067931,
            (float)-0.04728881,
            (float)0.02637685,
            (float)-0.009105732,
            (float)0.11824068,
            (float)-0.0074442136,
            (float)-0.0017491812,
            (float)-0.00877364,
            (float)-0.0015609923,
            (float)0.0017484532,
            (float)-0.034195714,
            (float)-0.37944683,
            (float)-0.18580982,
            (float)-0.004290643,
            (float)0.076832116,
            (float)-0.08118341,
            (float)-0.0029516662,
            (float)-0.08298985,
            (float)8.515259E-4,
            (float)0.0388696,
            (float)0.0056303735,
            (float)-0.088362195,
            (float)-0.0043656537,
            (float)0.036689714,
            (float)5.022884E-4,
            (float)0.0722052,
            (float)0.04355534,
            (float)-0.11684449,
            (float)-0.03165217,
            (float)-6.3094014E-4,
            (float)-0.112890415,
            (float)-0.0027963652,
            (float)-0.0055491924,
            (float)0.001167493,
            (float)0.008026322,
            (float)0.011334135,
            (float)-0.11801551,
            (float)0.071878135,
            (float)-0.007819756,
            (float)-0.10279106,
            (float)-0.004865032,
            (float)-0.0010058173,
            (float)0.0038561553,
            (float)-0.2787908,
            (float)-0.0023391778,
            (float)0.006820369,
            (float)-0.014370384,
            (float)0.0029755884,
            (float)0.062075783,
            (float)0.109803885,
            (float)0.001899402,
            (float)0.009595223,
            (float)-0.0032028651,
            (float)0.13817607,
            (float)-0.14200169,
            (float)-0.08308231,
            (float)0.0017186994,
            (float)0.0020615018,
            (float)0.018963233,
            (float)0.025763482,
            (float)-0.030205905,
            (float)-0.081184305,
            (float)-0.14459115,
            (float)0.0033497894,
            (float)0.03287358,
            (float)-0.0013045969,
            (float)0.0024044297,
            (float)-0.0024883475,
            (float)4.594045E-4,
            (float)-0.007056111,
            (float)-0.0024186408,
            (float)-0.055861548,
            (float)0.006042276,
            (float)0.015069608,
            (float)0.0034020117,
            (float)0.05551975,
            (float)-0.0027500885,
            (float)0.004490929,
            (float)0.28795117,
            (float)-2.5589543E-4,
            (float)0.04421416,
            (float)-0.0068091154,
            (float)-0.0071472237,
            (float)-0.23657906,
            (float)0.09087688,
            (float)-0.18740799,
            (float)0.015023408,
            (float)0.063090466,
            (float)0.0021748387,
            (float)0.0011450807,
            (float)9.0246E-4,
            (float)0.0052105067,
            (float)-0.006659237,
            (float)-0.011952981,
            (float)0.14801861,
            (float)-0.0056110844,
            (float)-0.0049894806,
            (float)0.0067208684,
            (float)8.734965E-4,
            (float)0.061914153,
            (float)0.0063667833,
            (float)1.09803485E-4,
            (float)0.0013494045,
            (float)-0.010165232,
            (float)-0.013609506,
            (float)-5.6369504E-4,
            (float)0.0030389237,
            (float)3.4854433E-4,
            (float)0.14562568,
            (float)-0.0032032402,
            (float)0.027437009,
            (float)0.006112541,
            (float)0.009124199,
            (float)7.635458E-5,
            (float)0.014372598,
            (float)0.014990426,
            (float)0.030275015,
            (float)0.10926025,
            (float)0.010814007,
            (float)-0.001417161,
            (float)0.006954606,
            (float)0.010565869,
            (float)-0.018834326,
            (float)0.012173037,
            (float)-8.669677E-4,
            (float)0.02710468,
            (float)8.9688244E-4,
            (float)-0.0028467095,
            (float)-0.003985748,
            (float)0.0010466577,
            (float)-0.002445548,
            (float)1.4016322E-4,
            (float)0.08562857,
            (float)0.0017320861,
            (float)-7.6171E-4,
            (float)-0.1812291,
            (float)-0.098210946,
            (float)0.0034371326,
            (float)-0.048629783,
            (float)0.051192194,
            (float)-0.0021887205,
            (float)-0.07677194,
            (float)0.025368217,
            (float)0.0015797666,
            (float)-5.454272E-4,
            (float)-0.009132913,
            (float)-0.07463279,
            (float)-0.0017504513,
            (float)-0.008633774,
            (float)-0.07693442,
            (float)-0.074693926,
            (float)0.0056241835,
            (float)0.009884656,
            (float)0.13112125,
            (float)0.0011867435,
            (float)-0.0010388329,
            (float)-0.0052652885};

    float [] eva2 = new float[]{
            (float)0.0037816989,
            (float)0.0015805087,
            (float)0.013343213,
            (float)0.0017652813,
            (float)0.016984707,
            (float)-0.051126666,
            (float)-0.058056965,
            (float)-0.06772965,
            (float)-0.050189305,
            (float)-0.20632109,
            (float)-0.013240155,
            (float)0.0063141645,
            (float)0.00168273,
            (float)0.012603338,
            (float)0.0013986871,
            (float)-0.07879464,
            (float)-0.011355874,
            (float)-0.0138902,
            (float)-8.0918346E-4,
            (float)-3.8384454E-4,
            (float)0.07861619,
            (float)-0.034475707,
            (float)-0.108440734,
            (float)0.0018031022,
            (float)0.049907763,
            (float)-0.023521964,
            (float)-0.010806222,
            (float)0.031114955,
            (float)-0.07794743,
            (float)-0.0343902,
            (float)0.013993276,
            (float)0.1920901,
            (float)-0.06381803,
            (float)-0.0020344267,
            (float)0.06819988,
            (float)-0.1772218,
            (float)-0.10115923,
            (float)0.04790618,
            (float)-0.010813844,
            (float)0.24895407,
            (float)7.440274E-4,
            (float)-5.3153466E-4,
            (float)0.0026282289,
            (float)-0.01580829,
            (float)-0.0020749378,
            (float)-0.026735095,
            (float)-0.020479178,
            (float)-0.18574348,
            (float)-0.0029854774,
            (float)0.115368895,
            (float)0.07090733,
            (float)-0.0016369977,
            (float)-0.11425314,
            (float)-6.003936E-4,
            (float)-0.13204747,
            (float)-0.008314444,
            (float)0.1008882,
            (float)-0.0016655826,
            (float)0.05229938,
            (float)0.0015102142,
            (float)-0.03277294,
            (float)0.05785577,
            (float)0.03855934,
            (float)0.061940346,
            (float)0.008740832,
            (float)-0.08334829,
            (float)-0.0045876256,
            (float)0.026087198,
            (float)-7.5872656E-4,
            (float)0.0023903362,
            (float)0.006728085,
            (float)-0.078170314,
            (float)0.21151944,
            (float)0.007818906,
            (float)-0.17516457,
            (float)0.013906435,
            (float)0.0020285435,
            (float)0.009327346,
            (float)-0.053594865,
            (float)0.08002245,
            (float)0.0017994028,
            (float)-0.020020539,
            (float)-0.002659606,
            (float)0.21351641,
            (float)0.08179577,
            (float)0.004011938,
            (float)0.0048512593,
            (float)0.01705614,
            (float)0.10864672,
            (float)-0.08130468,
            (float)0.049407348,
            (float)0.009024682,
            (float)2.718123E-4,
            (float)-0.0053469436,
            (float)0.05864479,
            (float)-0.16739202,
            (float)0.032030415,
            (float)-0.030695414,
            (float)0.0074551594,
            (float)0.0021074172,
            (float)-0.001443346,
            (float)-0.004151592,
            (float)-0.0031239528,
            (float)-5.054405E-4,
            (float)9.3436986E-4,
            (float)-8.181954E-5,
            (float)-0.058751382,
            (float)-0.0062992326,
            (float)-0.0053136228,
            (float)2.1391604E-4,
            (float)0.057546113,
            (float)-0.002434666,
            (float)-0.008358669,
            (float)0.14814863,
            (float)-0.0053716414,
            (float)0.05920207,
            (float)0.003812694,
            (float)-0.011635181,
            (float)0.02559548,
            (float)0.075045265,
            (float)-0.14136674,
            (float)0.018200375,
            (float)0.036456354,
            (float)-6.287825E-4,
            (float)-0.004640434,
            (float)-0.0036881296,
            (float)0.0044566654,
            (float)-0.0017439602,
            (float)0.0012533009,
            (float)0.07269605,
            (float)0.0014814715,
            (float)0.006014317,
            (float)0.0056714853,
            (float)0.077495694,
            (float)0.06534725,
            (float)0.0116802305,
            (float)0.06175192,
            (float)0.11015036,
            (float)-0.0047764545,
            (float)4.6035837E-4,
            (float)-0.011554071,
            (float)-0.0010481147,
            (float)0.008941287,
            (float)0.06379972,
            (float)-0.10141453,
            (float)0.019140504,
            (float)0.0043454585,
            (float)0.009066593,
            (float)-0.0027532915,
            (float)0.023801949,
            (float)-0.0033472553,
            (float)0.06540327,
            (float)0.092514165,
            (float)1.9384928E-5,
            (float)0.0019655195,
            (float)0.00727298,
            (float)0.017452875,
            (float)0.0015620037,
            (float)0.13632075,
            (float)-0.0048323097,
            (float)0.014559462,
            (float)0.0037469096,
            (float)-0.0060659978,
            (float)-0.003480352,
            (float)0.0022650557,
            (float)-0.00692355,
            (float)0.013924759,
            (float)-0.0552933,
            (float)-0.0031370132,
            (float)0.0024545584,
            (float)-0.2629234,
            (float)-0.019046856,
            (float)7.381808E-4,
            (float)-0.0944258,
            (float)-0.0029848297,
            (float)-0.001954597,
            (float)-0.041235372,
            (float)0.03837082,
            (float)-0.0021660335,
            (float)0.012251452,
            (float)-0.18338895,
            (float)0.20421195,
            (float)8.4247685E-4,
            (float)6.03014E-4,
            (float)0.1333379,
            (float)-0.18247178,
            (float)-0.1134475,
            (float)-0.08318373,
            (float)0.2630731,
            (float)0.014323584,
            (float)9.0670894E-4,
            (float)-0.0033950128};
    float [] eva3 = new float[]{
            (float)-0.0011226428,
            (float)1.6301317E-4,
            (float)0.013062753,
            (float)0.0064586033,
            (float)0.008003414,
            (float)0.066207826,
            (float)0.023029337,
            (float)-0.027078103,
            (float)-0.09016231,
            (float)-0.052266825,
            (float)-0.005048239,
            (float)0.0012574252,
            (float)0.007461814,
            (float)0.02353068,
            (float)0.0018968817,
            (float)-0.018278554,
            (float)0.0016673311,
            (float)-0.014990017,
            (float)-0.0012062108,
            (float)-0.0054686884,
            (float)0.09621455,
            (float)-0.023115784,
            (float)-0.16670482,
            (float)0.0030832354,
            (float)0.09605809,
            (float)-0.012160361,
            (float)-0.005743957,
            (float)0.06305585,
            (float)-0.08157195,
            (float)0.2023819,
            (float)0.012026372,
            (float)0.052697364,
            (float)-0.023809431,
            (float)0.0040147016,
            (float)-0.123978764,
            (float)8.857703E-4,
            (float)-0.17399442,
            (float)0.045039166,
            (float)-0.012967945,
            (float)0.39061725,
            (float)-2.3824091E-4,
            (float)-7.469842E-4,
            (float)6.622813E-4,
            (float)-0.016335368,
            (float)-0.013347629,
            (float)-0.013142058,
            (float)-0.06421104,
            (float)-0.21379939,
            (float)-0.009059932,
            (float)0.13292629,
            (float)0.10480417,
            (float)-4.1258612E-5,
            (float)-0.021084419,
            (float)-0.0015078491,
            (float)-0.08235224,
            (float)-0.013845553,
            (float)0.04062657,
            (float)-0.0019220926,
            (float)0.022534164,
            (float)0.002764912,
            (float)0.039879605,
            (float)0.01806263,
            (float)-0.023199776,
            (float)0.024232287,
            (float)0.0060159597,
            (float)-0.18813317,
            (float)-0.00431342,
            (float)0.020148315,
            (float)-0.006204422,
            (float)0.0010088099,
            (float)0.010249182,
            (float)-0.12298176,
            (float)0.27879888,
            (float)0.009667882,
            (float)-0.15107575,
            (float)-2.5864097E-4,
            (float)-0.001608553,
            (float)0.004459888,
            (float)-0.11948705,
            (float)0.16026552,
            (float)0.0052535464,
            (float)-0.023041977,
            (float)-0.0037210928,
            (float)0.09380143,
            (float)0.012241244,
            (float)0.0038175976,
            (float)0.0016624394,
            (float)0.049234923,
            (float)0.16156946,
            (float)-0.11041116,
            (float)0.019947667,
            (float)0.007287661,
            (float)3.3436732E-5,
            (float)-0.008685966,
            (float)0.19130832,
            (float)-0.049389523,
            (float)0.07506797,
            (float)0.025889477,
            (float)0.013703164,
            (float)-7.441311E-4,
            (float)-0.0020757457,
            (float)0.006299625,
            (float)-0.004969003,
            (float)0.0018415792,
            (float)0.0015637825,
            (float)-5.3463133E-5,
            (float)-0.089723915,
            (float)-0.0027100795,
            (float)-0.016840193,
            (float)0.0086064795,
            (float)0.1919671,
            (float)0.0017158001,
            (float)-0.0024358784,
            (float)0.1014062,
            (float)-7.4514863E-4,
            (float)-0.006535994,
            (float)-0.0016030029,
            (float)-0.02933136,
            (float)-0.04123243,
            (float)0.10709711,
            (float)-0.21760437,
            (float)-0.013570676,
            (float)0.06974475,
            (float)0.0067909965,
            (float)-0.0029811375,
            (float)-0.002474256,
            (float)0.009941185,
            (float)-0.0047903657,
            (float)-1.0549427E-4,
            (float)0.09381233,
            (float)1.7397261E-4,
            (float)6.986752E-4,
            (float)0.004475529,
            (float)0.015125822,
            (float)0.10299357,
            (float)0.0044033867,
            (float)-0.035901956,
            (float)0.008030995,
            (float)-0.0055409316,
            (float)-0.0048993733,
            (float)-0.0052902508,
            (float)-0.0015493926,
            (float)0.0038251786,
            (float)0.099168316,
            (float)-0.01814566,
            (float)0.0468278,
            (float)0.004149307,
            (float)0.006642712,
            (float)-0.006300746,
            (float)0.013633132,
            (float)0.0027112681,
            (float)0.065159105,
            (float)-0.005035477,
            (float)-0.0016069737,
            (float)0.008063783,
            (float)0.0032349587,
            (float)0.0069672023,
            (float)-0.0027824559,
            (float)-0.0013823042,
            (float)-0.0034939607,
            (float)0.010517337,
            (float)-0.001126659,
            (float)-0.0018988336,
            (float)-0.0037485042,
            (float)-0.0011902923,
            (float)-3.1574807E-4,
            (float)7.944386E-4,
            (float)-0.0010906216,
            (float)-8.0618315E-4,
            (float)0.0018120724,
            (float)-0.12894832,
            (float)0.0026327146,
            (float)0.0015544011,
            (float)-0.06629201,
            (float)0.038214765,
            (float)-0.0020671205,
            (float)-0.108833864,
            (float)0.02375936,
            (float)-0.0020545805,
            (float)0.007727892,
            (float)-0.14190628,
            (float)0.11736966,
            (float)-0.010022629,
            (float)-0.004235454,
            (float)0.07602624,
            (float)0.039185632,
            (float)-0.07068927,
            (float)-0.084071524,
            (float)0.2073631,
            (float)-0.086662695,
            (float)0.007237666,
            (float)-1.1184323E-4};
    float [] nicole1 = new float[]{
            (float)-0.008784431,
            (float)0.0020583053,
            (float)0.007969569,
            (float)0.025364777,
            (float)0.0026801904,
            (float)0.0049580354,
            (float)-0.12406991,
            (float)0.06285545,
            (float)0.03023469,
            (float)-0.08009103,
            (float)-0.020068178,
            (float)-0.008951495,
            (float)0.0020988984,
            (float)-0.0149190305,
            (float)0.0015097373,
            (float)0.02560844,
            (float)-2.593823E-4,
            (float)-0.0015527925,
            (float)-2.7998624E-4,
            (float)0.009512782,
            (float)-0.055968616,
            (float)-0.006197153,
            (float)-0.1051773,
            (float)0.005575162,
            (float)-0.14866494,
            (float)-0.0054389597,
            (float)-0.009622962,
            (float)0.050688803,
            (float)-0.08477045,
            (float)0.097382694,
            (float)0.0058562867,
            (float)0.0877809,
            (float)0.02040357,
            (float)-0.0020886809,
            (float)0.12206682,
            (float)-0.16283022,
            (float)-0.324409,
            (float)0.012527962,
            (float)-2.670158E-4,
            (float)0.009641871,
            (float)0.010046599,
            (float)0.0019583434,
            (float)-0.0012462026,
            (float)-9.061852E-4,
            (float)-3.714729E-4,
            (float)-0.021289567,
            (float)-0.055911057,
            (float)-0.04523732,
            (float)-0.0088251615,
            (float)1.0051123E-4,
            (float)0.12945029,
            (float)0.005700975,
            (float)0.06966772,
            (float)6.591432E-4,
            (float)-0.14120716,
            (float)-0.0032281224,
            (float)0.13789406,
            (float)6.252623E-4,
            (float)0.014055417,
            (float)0.001593578,
            (float)-0.057149254,
            (float)0.068096384,
            (float)-0.011492079,
            (float)-0.1569229,
            (float)-0.016326,
            (float)0.14186497,
            (float)-0.0011953311,
            (float)0.02631479,
            (float)0.0055191107,
            (float)-0.0041767377,
            (float)0.003636564,
            (float)-0.1023649,
            (float)0.3388332,
            (float)-0.015774254,
            (float)-0.022308795,
            (float)7.6405075E-4,
            (float)0.0030483808,
            (float)4.5260708E-4,
            (float)0.1712395,
            (float)-0.17207015,
            (float)6.9881696E-4,
            (float)-0.073779486,
            (float)0.0027667554,
            (float)0.02499354,
            (float)0.0856461,
            (float)-2.868015E-4,
            (float)0.0010221738,
            (float)-0.015413669,
            (float)0.08716239,
            (float)-0.09604756,
            (float)-0.01742344,
            (float)-0.0026569543,
            (float)-0.0053967712,
            (float)-7.8080903E-4,
            (float)0.06530905,
            (float)-0.06694213,
            (float)-0.024471205,
            (float)-0.02483774,
            (float)0.007162042,
            (float)0.0036638537,
            (float)0.0021204296,
            (float)-0.0034327274,
            (float)-0.005271671,
            (float)-0.0018939351,
            (float)7.3401706E-4,
            (float)-0.0034231856,
            (float)-0.12549621,
            (float)0.0025236215,
            (float)-0.0049703973,
            (float)-0.0013942113,
            (float)-0.14265469,
            (float)-8.8502927E-4,
            (float)-5.974023E-4,
            (float)0.16821365,
            (float)-0.014892407,
            (float)-0.056733407,
            (float)0.02072485,
            (float)-0.008406544,
            (float)0.17233163,
            (float)0.11011456,
            (float)-0.1443782,
            (float)0.015569852,
            (float)0.041670386,
            (float)-3.7787296E-4,
            (float)-0.0073305173,
            (float)-0.005616217,
            (float)-0.0014128997,
            (float)-0.0036009871,
            (float)0.010775573,
            (float)-0.17393649,
            (float)0.0036758853,
            (float)0.0075464547,
            (float)0.0038131133,
            (float)0.13845566,
            (float)-0.023742618,
            (float)0.011120651,
            (float)0.10631994,
            (float)0.1094536,
            (float)-0.0014773246,
            (float)-0.006248091,
            (float)0.002322431,
            (float)0.004722807,
            (float)0.005822256,
            (float)0.07402234,
            (float)-0.06301692,
            (float)0.044891316,
            (float)-0.023515778,
            (float)-0.011708865,
            (float)-0.011462534,
            (float)-0.0023382371,
            (float)-0.0080687115,
            (float)0.10709734,
            (float)0.0026609572,
            (float)5.862983E-4,
            (float)-0.0016256571,
            (float)0.018566687,
            (float)-0.0095005585,
            (float)-0.010369906,
            (float)0.04820027,
            (float)2.4393905E-4,
            (float)-0.0053879186,
            (float)0.0029653443,
            (float)0.009258043,
            (float)0.003048112,
            (float)0.01484629,
            (float)-0.005333292,
            (float)-0.0012392837,
            (float)0.056466036,
            (float)1.6186846E-4,
            (float)-0.009337505,
            (float)-0.30496618,
            (float)0.05623847,
            (float)0.002063928,
            (float)0.043723144,
            (float)0.07301938,
            (float)-0.004559742,
            (float)0.06699092,
            (float)0.031774387,
            (float)7.8624906E-4,
            (float)0.006680473,
            (float)0.05258881,
            (float)0.016476702,
            (float)0.011412881,
            (float)0.003353218,
            (float)0.20297492,
            (float)0.055920884,
            (float)0.018344516,
            (float)-0.057187166,
            (float)0.12790091,
            (float)-0.0032607552,
            (float)0.0045928215,
            (float)-0.017121062};

    float [] nicole2 = new float[]{
            (float)-0.009379515,
            (float)0.005858567,
            (float)0.019251348,
            (float)0.007186283,
            (float)-0.015314526,
            (float)-0.001901494,
            (float)-0.086235486,
            (float)0.046430554,
            (float)0.022973116,
            (float)-0.11268288,
            (float)-0.011717672,
            (float)0.0047647106,
            (float)-0.0011362706,
            (float)0.014298302,
            (float)-0.0013095501,
            (float)-0.13548715,
            (float)-0.0064098327,
            (float)-0.008952562,
            (float)0.0019149282,
            (float)0.001752813,
            (float)-0.11352187,
            (float)0.0072770505,
            (float)-0.10063138,
            (float)0.0052738143,
            (float)0.07825605,
            (float)-0.011819782,
            (float)-0.020046564,
            (float)0.048377737,
            (float)-0.08612333,
            (float)0.005907539,
            (float)0.021487044,
            (float)0.17470318,
            (float)0.068856664,
            (float)-0.0037579553,
            (float)-0.02582163,
            (float)-0.2432936,
            (float)-0.080185555,
            (float)0.015947664,
            (float)-0.0020698728,
            (float)-0.0294171,
            (float)0.002753292,
            (float)0.0015204867,
            (float)-6.8580586E-4,
            (float)-8.2166697E-4,
            (float)-0.004690698,
            (float)-0.018311393,
            (float)-0.098982014,
            (float)-0.15730484,
            (float)-0.005569839,
            (float)0.097821966,
            (float)0.1894429,
            (float)0.0026106923,
            (float)-0.13778895,
            (float)-0.0019013223,
            (float)-0.12819637,
            (float)-0.0022098143,
            (float)0.13242276,
            (float)-0.00116917,
            (float)0.0030348587,
            (float)0.0137272095,
            (float)0.028052412,
            (float)0.012289036,
            (float)0.03521318,
            (float)-0.05560725,
            (float)0.0017058462,
            (float)0.19407082,
            (float)-0.005852402,
            (float)0.010256938,
            (float)-0.0013211289,
            (float)-0.0025852062,
            (float)-0.0016128588,
            (float)-0.13586316,
            (float)0.19850475,
            (float)-0.0012017477,
            (float)-0.06633088,
            (float)0.01834917,
            (float)0.001063859,
            (float)0.007007668,
            (float)0.03633696,
            (float)-0.0883604,
            (float)0.0018326226,
            (float)-0.048784915,
            (float)0.0020785092,
            (float)0.12032615,
            (float)0.15127037,
            (float)-1.836603E-4,
            (float)0.010256842,
            (float)0.057215687,
            (float)0.07426989,
            (float)-0.25919408,
            (float)0.08186132,
            (float)-0.0038530533,
            (float)-0.0048381025,
            (float)0.0027205984,
            (float)0.012049549,
            (float)-0.29697278,
            (float)-0.056124274,
            (float)0.0027083228,
            (float)0.0056799925,
            (float)-0.00981825,
            (float)0.0012895309,
            (float)-3.9882044E-4,
            (float)-0.005484883,
            (float)0.0024153597,
            (float)6.3243276E-4,
            (float)6.5805647E-4,
            (float)-0.24694304,
            (float)-0.0016618834,
            (float)0.0052571655,
            (float)0.010322038,
            (float)-0.15393423,
            (float)0.0033038605,
            (float)6.1289594E-4,
            (float)0.011047812,
            (float)-0.001316439,
            (float)0.019923275,
            (float)8.70823E-4,
            (float)-0.003702431,
            (float)-0.041707624,
            (float)0.1757715,
            (float)-0.027013715,
            (float)0.011950547,
            (float)-0.11295233,
            (float)-0.002005625,
            (float)-0.004203847,
            (float)-0.003505531,
            (float)0.002588244,
            (float)-0.004824583,
            (float)0.009650158,
            (float)0.07890622,
            (float)0.0019843623,
            (float)0.015591449,
            (float)0.0038927125,
            (float)0.1909846,
            (float)-0.0299526,
            (float)0.0055767614,
            (float)-0.012358368,
            (float)-0.03144851,
            (float)-0.012910105,
            (float)0.006718921,
            (float)-0.007249775,
            (float)0.0011523438,
            (float)0.0040379874,
            (float)-0.026219541,
            (float)-0.111876726,
            (float)0.2109797,
            (float)0.010133454,
            (float)6.3160784E-4,
            (float)0.0034628317,
            (float)0.0064922418,
            (float)-0.001750192,
            (float)-0.013294956,
            (float)-0.009111165,
            (float)-0.0053397864,
            (float)-0.0019032883,
            (float)0.012054914,
            (float)0.0017768709,
            (float)0.00868583,
            (float)0.042471815,
            (float)-0.0010192662,
            (float)-0.022752397,
            (float)-2.7330752E-4,
            (float)3.677219E-4,
            (float)-8.3792093E-4,
            (float)0.0032707616,
            (float)0.0041562314,
            (float)0.008529456,
            (float)-0.031654768,
            (float)-0.0033037202,
            (float)-0.00418323,
            (float)-0.19594269,
            (float)-0.006781891,
            (float)0.003933388,
            (float)0.022485368,
            (float)0.02841615,
            (float)-0.0018037998,
            (float)-0.026460098,
            (float)0.0033320512,
            (float)-0.0039528636,
            (float)0.010008731,
            (float)-0.070712715,
            (float)0.16913036,
            (float)0.0020500813,
            (float)-0.0010581142,
            (float)0.09464176,
            (float)-0.012551679,
            (float)-0.04486077,
            (float)-0.04618461,
            (float)0.10325868,
            (float)-0.1221152,
            (float)-0.0105159255,
            (float)-0.008631823};

        public float[] getEva1() {
                return eva1;
        }

        public float[] getEva2() {
                return eva2;
        }

        public float[] getEva3() {
                return eva3;
        }

        public float[] getNicole1() {
                return nicole1;
        }

        public float[] getNicole2() {
                return nicole2;
        }
}
