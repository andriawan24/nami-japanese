package com.namijapanese.core.data.repository

import com.namijapanese.core.data.KanaData
import com.namijapanese.core.model.KanaCharacter
import com.namijapanese.core.model.KanaGroup
import com.namijapanese.core.model.KanaType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KanaRepository @Inject constructor() {
    
    fun getCharacters(type: KanaType): List<KanaCharacter> = 
        KanaData.getByType(type)
    
    fun getCharacterById(id: String): KanaCharacter? = 
        KanaData.getById(id)
    
    fun getCharactersByGroup(type: KanaType, group: KanaGroup): List<KanaCharacter> = 
        KanaData.getByGroup(type, group)
    
    fun searchCharacters(query: String, type: KanaType): List<KanaCharacter> = 
        KanaData.getByType(type).filter { character ->
            character.character.contains(query) ||
            character.romaji.contains(query, ignoreCase = true)
        }
}
