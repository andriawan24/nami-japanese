package com.namijapanese.core.data

import com.namijapanese.core.model.KanaCharacter
import com.namijapanese.core.model.KanaGroup
import com.namijapanese.core.model.KanaType

object KanaData {
    val hiragana: List<KanaCharacter> = listOf(
        KanaCharacter("h_a", "あ", "a", KanaType.HIRAGANA, KanaGroup.A, 2, 1,
            exampleWord = "あめ", exampleWordReading = "ame", exampleWordMeaning = "rain",
            exampleSentence = "あめがふっています。", exampleSentenceReading = "Ame ga futte imasu.", exampleSentenceMeaning = "It is raining."),
        KanaCharacter("h_i", "い", "i", KanaType.HIRAGANA, KanaGroup.A, 2, 2,
            exampleWord = "いぬ", exampleWordReading = "inu", exampleWordMeaning = "dog",
            exampleSentence = "いぬがいます。", exampleSentenceReading = "Inu ga imasu.", exampleSentenceMeaning = "There is a dog."),
        KanaCharacter("h_u", "う", "u", KanaType.HIRAGANA, KanaGroup.A, 2, 3,
            exampleWord = "うみ", exampleWordReading = "umi", exampleWordMeaning = "sea",
            exampleSentence = "うみがきれいです。", exampleSentenceReading = "Umi ga kirei desu.", exampleSentenceMeaning = "The sea is beautiful."),
        KanaCharacter("h_e", "え", "e", KanaType.HIRAGANA, KanaGroup.A, 3, 4,
            exampleWord = "えき", exampleWordReading = "eki", exampleWordMeaning = "station",
            exampleSentence = "えきにいきます。", exampleSentenceReading = "Eki ni ikimasu.", exampleSentenceMeaning = "I go to the station."),
        KanaCharacter("h_o", "お", "o", KanaType.HIRAGANA, KanaGroup.A, 3, 5,
            exampleWord = "おにぎり", exampleWordReading = "onigiri", exampleWordMeaning = "rice ball",
            exampleSentence = "おにぎりをたべます。", exampleSentenceReading = "Onigiri o tabemasu.", exampleSentenceMeaning = "I eat a rice ball."),

        KanaCharacter("h_ka", "か", "ka", KanaType.HIRAGANA, KanaGroup.KA, 3, 6,
            exampleWord = "かさ", exampleWordReading = "kasa", exampleWordMeaning = "umbrella",
            exampleSentence = "かさをさします。", exampleSentenceReading = "Kasa o sashimasu.", exampleSentenceMeaning = "I use an umbrella."),
        KanaCharacter("h_ki", "き", "ki", KanaType.HIRAGANA, KanaGroup.KA, 4, 7,
            exampleWord = "き", exampleWordReading = "ki", exampleWordMeaning = "tree",
            exampleSentence = "きがたかいです。", exampleSentenceReading = "Ki ga takai desu.", exampleSentenceMeaning = "The tree is tall."),
        KanaCharacter("h_ku", "く", "ku", KanaType.HIRAGANA, KanaGroup.KA, 1, 8,
            exampleWord = "くつ", exampleWordReading = "kutsu", exampleWordMeaning = "shoes",
            exampleSentence = "くつをはきます。", exampleSentenceReading = "Kutsu o hakimasu.", exampleSentenceMeaning = "I wear shoes."),
        KanaCharacter("h_ke", "け", "ke", KanaType.HIRAGANA, KanaGroup.KA, 3, 9,
            exampleWord = "けむり", exampleWordReading = "kemuri", exampleWordMeaning = "smoke",
            exampleSentence = "けむりがみえます。", exampleSentenceReading = "Kemuri ga miemasu.", exampleSentenceMeaning = "I can see smoke."),
        KanaCharacter("h_ko", "こ", "ko", KanaType.HIRAGANA, KanaGroup.KA, 2, 10,
            exampleWord = "こえ", exampleWordReading = "koe", exampleWordMeaning = "voice",
            exampleSentence = "こえがきこえます。", exampleSentenceReading = "Koe ga kikoemasu.", exampleSentenceMeaning = "I can hear a voice."),

        KanaCharacter("h_sa", "さ", "sa", KanaType.HIRAGANA, KanaGroup.SA, 3, 11,
            exampleWord = "さかな", exampleWordReading = "sakana", exampleWordMeaning = "fish",
            exampleSentence = "さかなをたべます。", exampleSentenceReading = "Sakana o tabemasu.", exampleSentenceMeaning = "I eat fish."),
        KanaCharacter("h_shi", "し", "shi", KanaType.HIRAGANA, KanaGroup.SA, 1, 12,
            exampleWord = "しお", exampleWordReading = "shio", exampleWordMeaning = "salt",
            exampleSentence = "しおをいれます。", exampleSentenceReading = "Shio o iremasu.", exampleSentenceMeaning = "I add salt."),
        KanaCharacter("h_su", "す", "su", KanaType.HIRAGANA, KanaGroup.SA, 3, 13,
            exampleWord = "すし", exampleWordReading = "sushi", exampleWordMeaning = "sushi",
            exampleSentence = "すしがすきです。", exampleSentenceReading = "Sushi ga suki desu.", exampleSentenceMeaning = "I like sushi."),
        KanaCharacter("h_se", "せ", "se", KanaType.HIRAGANA, KanaGroup.SA, 3, 14,
            exampleWord = "せんせい", exampleWordReading = "sensei", exampleWordMeaning = "teacher",
            exampleSentence = "せんせいがいます。", exampleSentenceReading = "Sensei ga imasu.", exampleSentenceMeaning = "There is a teacher."),
        KanaCharacter("h_so", "そ", "so", KanaType.HIRAGANA, KanaGroup.SA, 3, 15,
            exampleWord = "そら", exampleWordReading = "sora", exampleWordMeaning = "sky",
            exampleSentence = "そらがあおいです。", exampleSentenceReading = "Sora ga aoi desu.", exampleSentenceMeaning = "The sky is blue."),

        KanaCharacter("h_ta", "た", "ta", KanaType.HIRAGANA, KanaGroup.TA, 4, 16,
            exampleWord = "たまご", exampleWordReading = "tamago", exampleWordMeaning = "egg",
            exampleSentence = "たまごをたべます。", exampleSentenceReading = "Tamago o tabemasu.", exampleSentenceMeaning = "I eat an egg."),
        KanaCharacter("h_chi", "ち", "chi", KanaType.HIRAGANA, KanaGroup.TA, 2, 17,
            exampleWord = "ちず", exampleWordReading = "chizu", exampleWordMeaning = "map",
            exampleSentence = "ちずをみます。", exampleSentenceReading = "Chizu o mimasu.", exampleSentenceMeaning = "I look at a map."),
        KanaCharacter("h_tsu", "つ", "tsu", KanaType.HIRAGANA, KanaGroup.TA, 3, 18,
            exampleWord = "つき", exampleWordReading = "tsuki", exampleWordMeaning = "moon",
            exampleSentence = "つきがきれいです。", exampleSentenceReading = "Tsuki ga kirei desu.", exampleSentenceMeaning = "The moon is beautiful."),
        KanaCharacter("h_te", "て", "te", KanaType.HIRAGANA, KanaGroup.TA, 2, 19,
            exampleWord = "て", exampleWordReading = "te", exampleWordMeaning = "hand",
            exampleSentence = "てをあらいます。", exampleSentenceReading = "Te o araimasu.", exampleSentenceMeaning = "I wash my hands."),
        KanaCharacter("h_to", "と", "to", KanaType.HIRAGANA, KanaGroup.TA, 2, 20,
            exampleWord = "とけい", exampleWordReading = "tokei", exampleWordMeaning = "clock",
            exampleSentence = "とけいをみます。", exampleSentenceReading = "Tokei o mimasu.", exampleSentenceMeaning = "I look at the clock."),

        KanaCharacter("h_na", "な", "na", KanaType.HIRAGANA, KanaGroup.NA, 4, 21),
        KanaCharacter("h_ni", "に", "ni", KanaType.HIRAGANA, KanaGroup.NA, 3, 22),
        KanaCharacter("h_nu", "ぬ", "nu", KanaType.HIRAGANA, KanaGroup.NA, 4, 23),
        KanaCharacter("h_ne", "ね", "ne", KanaType.HIRAGANA, KanaGroup.NA, 4, 24),
        KanaCharacter("h_no", "の", "no", KanaType.HIRAGANA, KanaGroup.NA, 2, 25),

        KanaCharacter("h_ha", "は", "ha", KanaType.HIRAGANA, KanaGroup.HA, 3, 26),
        KanaCharacter("h_hi", "ひ", "hi", KanaType.HIRAGANA, KanaGroup.HA, 2, 27),
        KanaCharacter("h_fu", "ふ", "fu", KanaType.HIRAGANA, KanaGroup.HA, 4, 28),
        KanaCharacter("h_he", "へ", "he", KanaType.HIRAGANA, KanaGroup.HA, 1, 29),
        KanaCharacter("h_ho", "ほ", "ho", KanaType.HIRAGANA, KanaGroup.HA, 4, 30),

        KanaCharacter("h_ma", "ま", "ma", KanaType.HIRAGANA, KanaGroup.MA, 3, 31),
        KanaCharacter("h_mi", "み", "mi", KanaType.HIRAGANA, KanaGroup.MA, 2, 32),
        KanaCharacter("h_mu", "む", "mu", KanaType.HIRAGANA, KanaGroup.MA, 4, 33),
        KanaCharacter("h_me", "め", "me", KanaType.HIRAGANA, KanaGroup.MA, 2, 34),
        KanaCharacter("h_mo", "も", "mo", KanaType.HIRAGANA, KanaGroup.MA, 3, 35),

        KanaCharacter("h_ya", "や", "ya", KanaType.HIRAGANA, KanaGroup.YA, 3, 36),
        KanaCharacter("h_yu", "ゆ", "yu", KanaType.HIRAGANA, KanaGroup.YA, 3, 37),
        KanaCharacter("h_yo", "よ", "yo", KanaType.HIRAGANA, KanaGroup.YA, 2, 38),

        KanaCharacter("h_ra", "ら", "ra", KanaType.HIRAGANA, KanaGroup.RA, 2, 39),
        KanaCharacter("h_ri", "り", "ri", KanaType.HIRAGANA, KanaGroup.RA, 2, 40),
        KanaCharacter("h_ru", "る", "ru", KanaType.HIRAGANA, KanaGroup.RA, 2, 41),
        KanaCharacter("h_re", "れ", "re", KanaType.HIRAGANA, KanaGroup.RA, 3, 42),
        KanaCharacter("h_ro", "ろ", "ro", KanaType.HIRAGANA, KanaGroup.RA, 2, 43),

        KanaCharacter("h_wa", "わ", "wa", KanaType.HIRAGANA, KanaGroup.WA, 2, 44),
        KanaCharacter("h_wo", "を", "wo", KanaType.HIRAGANA, KanaGroup.WA, 2, 45),
        KanaCharacter("h_n", "ん", "n", KanaType.HIRAGANA, KanaGroup.WA, 2, 46)
    )

    val katakana: List<KanaCharacter> = listOf(
        KanaCharacter("k_a", "ア", "a", KanaType.KATAKANA, KanaGroup.A, 2, 1,
            exampleWord = "アイス", exampleWordReading = "aisu", exampleWordMeaning = "ice cream",
            exampleSentence = "アイスをたべます。", exampleSentenceReading = "Aisu o tabemasu.", exampleSentenceMeaning = "I eat ice cream."),
        KanaCharacter("k_i", "イ", "i", KanaType.KATAKANA, KanaGroup.A, 2, 2,
            exampleWord = "インク", exampleWordReading = "inku", exampleWordMeaning = "ink",
            exampleSentence = "インクをつかいます。", exampleSentenceReading = "Inku o tsukaimasu.", exampleSentenceMeaning = "I use ink."),
        KanaCharacter("k_u", "ウ", "u", KanaType.KATAKANA, KanaGroup.A, 3, 3,
            exampleWord = "ウール", exampleWordReading = "uuru", exampleWordMeaning = "wool",
            exampleSentence = "ウールのセーターです。", exampleSentenceReading = "Uuru no seetaa desu.", exampleSentenceMeaning = "It is a wool sweater."),
        KanaCharacter("k_e", "エ", "e", KanaType.KATAKANA, KanaGroup.A, 3, 4,
            exampleWord = "エアコン", exampleWordReading = "eakon", exampleWordMeaning = "air conditioner",
            exampleSentence = "エアコンをつけます。", exampleSentenceReading = "Eakon o tsukemasu.", exampleSentenceMeaning = "I turn on the air conditioner."),
        KanaCharacter("k_o", "オ", "o", KanaType.KATAKANA, KanaGroup.A, 3, 5,
            exampleWord = "オレンジ", exampleWordReading = "orenji", exampleWordMeaning = "orange",
            exampleSentence = "オレンジをのみます。", exampleSentenceReading = "Orenji o nomimasu.", exampleSentenceMeaning = "I drink orange juice."),

        KanaCharacter("k_ka", "カ", "ka", KanaType.KATAKANA, KanaGroup.KA, 2, 6,
            exampleWord = "カメラ", exampleWordReading = "kamera", exampleWordMeaning = "camera",
            exampleSentence = "カメラをつかいます。", exampleSentenceReading = "Kamera o tsukaimasu.", exampleSentenceMeaning = "I use a camera."),
        KanaCharacter("k_ki", "キ", "ki", KanaType.KATAKANA, KanaGroup.KA, 3, 7,
            exampleWord = "キウイ", exampleWordReading = "kiui", exampleWordMeaning = "kiwi",
            exampleSentence = "キウイをたべます。", exampleSentenceReading = "Kiui o tabemasu.", exampleSentenceMeaning = "I eat kiwi."),
        KanaCharacter("k_ku", "ク", "ku", KanaType.KATAKANA, KanaGroup.KA, 2, 8,
            exampleWord = "クラス", exampleWordReading = "kurasu", exampleWordMeaning = "class",
            exampleSentence = "クラスがはじまります。", exampleSentenceReading = "Kurasu ga hajimarimasu.", exampleSentenceMeaning = "Class begins."),
        KanaCharacter("k_ke", "ケ", "ke", KanaType.KATAKANA, KanaGroup.KA, 2, 9,
            exampleWord = "ケーキ", exampleWordReading = "keeki", exampleWordMeaning = "cake",
            exampleSentence = "ケーキをたべます。", exampleSentenceReading = "Keeki o tabemasu.", exampleSentenceMeaning = "I eat cake."),
        KanaCharacter("k_ko", "コ", "ko", KanaType.KATAKANA, KanaGroup.KA, 2, 10,
            exampleWord = "コーヒー", exampleWordReading = "koohii", exampleWordMeaning = "coffee",
            exampleSentence = "コーヒーをのみます。", exampleSentenceReading = "Koohii o nomimasu.", exampleSentenceMeaning = "I drink coffee."),

        KanaCharacter("k_sa", "サ", "sa", KanaType.KATAKANA, KanaGroup.SA, 3, 11,
            exampleWord = "サラダ", exampleWordReading = "sarada", exampleWordMeaning = "salad",
            exampleSentence = "サラダをたべます。", exampleSentenceReading = "Sarada o tabemasu.", exampleSentenceMeaning = "I eat salad."),
        KanaCharacter("k_shi", "シ", "shi", KanaType.KATAKANA, KanaGroup.SA, 2, 12,
            exampleWord = "シャツ", exampleWordReading = "shatsu", exampleWordMeaning = "shirt",
            exampleSentence = "シャツをきます。", exampleSentenceReading = "Shatsu o kimasu.", exampleSentenceMeaning = "I wear a shirt."),
        KanaCharacter("k_su", "ス", "su", KanaType.KATAKANA, KanaGroup.SA, 2, 13,
            exampleWord = "スープ", exampleWordReading = "suupu", exampleWordMeaning = "soup",
            exampleSentence = "スープをのみます。", exampleSentenceReading = "Suupu o nomimasu.", exampleSentenceMeaning = "I drink soup."),
        KanaCharacter("k_se", "セ", "se", KanaType.KATAKANA, KanaGroup.SA, 2, 14,
            exampleWord = "セーター", exampleWordReading = "seetaa", exampleWordMeaning = "sweater",
            exampleSentence = "セーターをきます。", exampleSentenceReading = "Seetaa o kimasu.", exampleSentenceMeaning = "I wear a sweater."),
        KanaCharacter("k_so", "ソ", "so", KanaType.KATAKANA, KanaGroup.SA, 2, 15,
            exampleWord = "ソファ", exampleWordReading = "sofa", exampleWordMeaning = "sofa",
            exampleSentence = "ソファにすわります。", exampleSentenceReading = "Sofa ni suwarimasu.", exampleSentenceMeaning = "I sit on the sofa."),

        KanaCharacter("k_ta", "タ", "ta", KanaType.KATAKANA, KanaGroup.TA, 3, 16,
            exampleWord = "タクシー", exampleWordReading = "takushii", exampleWordMeaning = "taxi",
            exampleSentence = "タクシーにのります。", exampleSentenceReading = "Takushii ni norimasu.", exampleSentenceMeaning = "I ride a taxi."),
        KanaCharacter("k_chi", "チ", "chi", KanaType.KATAKANA, KanaGroup.TA, 2, 17,
            exampleWord = "チーズ", exampleWordReading = "chiizu", exampleWordMeaning = "cheese",
            exampleSentence = "チーズをたべます。", exampleSentenceReading = "Chiizu o tabemasu.", exampleSentenceMeaning = "I eat cheese."),
        KanaCharacter("k_tsu", "ツ", "tsu", KanaType.KATAKANA, KanaGroup.TA, 3, 18,
            exampleWord = "ツアー", exampleWordReading = "tsuaa", exampleWordMeaning = "tour",
            exampleSentence = "ツアーにいきます。", exampleSentenceReading = "Tsuaa ni ikimasu.", exampleSentenceMeaning = "I go on a tour."),
        KanaCharacter("k_te", "テ", "te", KanaType.KATAKANA, KanaGroup.TA, 3, 19,
            exampleWord = "テレビ", exampleWordReading = "terebi", exampleWordMeaning = "television",
            exampleSentence = "テレビをみます。", exampleSentenceReading = "Terebi o mimasu.", exampleSentenceMeaning = "I watch television."),
        KanaCharacter("k_to", "ト", "to", KanaType.KATAKANA, KanaGroup.TA, 2, 20,
            exampleWord = "トマト", exampleWordReading = "tomato", exampleWordMeaning = "tomato",
            exampleSentence = "トマトをたべます。", exampleSentenceReading = "Tomato o tabemasu.", exampleSentenceMeaning = "I eat a tomato."),

        KanaCharacter("k_na", "ナ", "na", KanaType.KATAKANA, KanaGroup.NA, 2, 21),
        KanaCharacter("k_ni", "ニ", "ni", KanaType.KATAKANA, KanaGroup.NA, 2, 22),
        KanaCharacter("k_nu", "ヌ", "nu", KanaType.KATAKANA, KanaGroup.NA, 2, 23),
        KanaCharacter("k_ne", "ネ", "ne", KanaType.KATAKANA, KanaGroup.NA, 3, 24),
        KanaCharacter("k_no", "ノ", "no", KanaType.KATAKANA, KanaGroup.NA, 1, 25),

        KanaCharacter("k_ha", "ハ", "ha", KanaType.KATAKANA, KanaGroup.HA, 2, 26),
        KanaCharacter("k_hi", "ヒ", "hi", KanaType.KATAKANA, KanaGroup.HA, 2, 27),
        KanaCharacter("k_fu", "フ", "fu", KanaType.KATAKANA, KanaGroup.HA, 1, 28),
        KanaCharacter("k_he", "ヘ", "he", KanaType.KATAKANA, KanaGroup.HA, 1, 29),
        KanaCharacter("k_ho", "ホ", "ho", KanaType.KATAKANA, KanaGroup.HA, 3, 30),

        KanaCharacter("k_ma", "マ", "ma", KanaType.KATAKANA, KanaGroup.MA, 2, 31),
        KanaCharacter("k_mi", "ミ", "mi", KanaType.KATAKANA, KanaGroup.MA, 3, 32),
        KanaCharacter("k_mu", "ム", "mu", KanaType.KATAKANA, KanaGroup.MA, 2, 33),
        KanaCharacter("k_me", "メ", "me", KanaType.KATAKANA, KanaGroup.MA, 2, 34),
        KanaCharacter("k_mo", "モ", "mo", KanaType.KATAKANA, KanaGroup.MA, 3, 35),

        KanaCharacter("k_ya", "ヤ", "ya", KanaType.KATAKANA, KanaGroup.YA, 2, 36),
        KanaCharacter("k_yu", "ユ", "yu", KanaType.KATAKANA, KanaGroup.YA, 2, 37),
        KanaCharacter("k_yo", "ヨ", "yo", KanaType.KATAKANA, KanaGroup.YA, 3, 38),

        KanaCharacter("k_ra", "ラ", "ra", KanaType.KATAKANA, KanaGroup.RA, 2, 39),
        KanaCharacter("k_ri", "リ", "ri", KanaType.KATAKANA, KanaGroup.RA, 2, 40),
        KanaCharacter("k_ru", "ル", "ru", KanaType.KATAKANA, KanaGroup.RA, 2, 41),
        KanaCharacter("k_re", "レ", "re", KanaType.KATAKANA, KanaGroup.RA, 1, 42),
        KanaCharacter("k_ro", "ロ", "ro", KanaType.KATAKANA, KanaGroup.RA, 3, 43),

        KanaCharacter("k_wa", "ワ", "wa", KanaType.KATAKANA, KanaGroup.WA, 2, 44),
        KanaCharacter("k_wo", "ヲ", "wo", KanaType.KATAKANA, KanaGroup.WA, 2, 45),
        KanaCharacter("k_n", "ン", "n", KanaType.KATAKANA, KanaGroup.WA, 2, 46)
    )

    fun getByType(type: KanaType): List<KanaCharacter> =
        if (type == KanaType.HIRAGANA) hiragana else katakana

    fun getById(id: String): KanaCharacter? =
        (hiragana + katakana).find { it.id == id }

    fun getByGroup(type: KanaType, group: KanaGroup): List<KanaCharacter> =
        getByType(type).filter { it.group == group }

    fun getAll(): List<KanaCharacter> = hiragana + katakana
}
