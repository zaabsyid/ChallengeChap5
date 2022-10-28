package com.zahirar.challengechap5.viewmodel

import com.zahirar.challengechap5.model.GetFilmResponseItem
import com.zahirar.challengechap5.network.APIInterface
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class ViewModelFilmTest{
    lateinit var servis : APIInterface

    @Before
    fun setUp(){
        servis = mockk()
    }

    @Test
    fun getFilmTest(): Unit = runBlocking {
//        mocking GIVEN
        val respAllFilm = mockk <Call<List<GetFilmResponseItem>>>()

        every {
            runBlocking {
                servis.getAllFilm()
            }
        } returns respAllFilm

//        System Under Test (WHEN)
        val result = servis.getAllFilm()

        verify {
            runBlocking { servis.getAllFilm() }
        }
        assertEquals(result, respAllFilm)

    }
    @Test
    fun testGetMakeUp(){
        val respAllFilm = mockk <Call<List<GetFilmResponseItem>>>()
        every {
            servis.getAllFilm()

        } returns respAllFilm

        //        System Under Test (WHEN)
        val result = servis.getAllFilm()

        verify {
            servis.getAllFilm()
        }
        Assert.assertEquals(result, respAllFilm)
    }
}