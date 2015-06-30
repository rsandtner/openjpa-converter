/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.github.rsandtner.openjpaconverter.test.global;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class GlobalValueHandlerTest
{
    private static EntityManagerFactory emf;

    @BeforeClass
    public static void setup()
    {
        emf = Persistence.createEntityManagerFactory("GLOBAL_PU");
    }

    @Test
    public void testWithDeclarationInPersistenceXML()
    {
        {
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            em.persist(new GlobalValueHandlerEntity("Global",
                                                    LocalDate.now(),
                                                    LocalDateTime.now(),
                                                    LocalTime.now()));

            em.getTransaction().commit();
            em.close();
        }

        {
            EntityManager em = emf.createEntityManager();

            List<GlobalValueHandlerEntity> result = em.createQuery("SELECT g FROM GlobalValueHandlerEntity AS g", GlobalValueHandlerEntity.class).getResultList();
            Assert.assertFalse(result.isEmpty());
        }
    }

    @Test
    public void testDeclarationInPersistenceXML_addQueryParameter()
    {
        GlobalValueHandlerEntity theOneChecked = null;

        // first setup data
        {
            EntityManager em = emf.createEntityManager();

            em.getTransaction().begin();

            for (int i = 0; i < 100; i++)
            {
                GlobalValueHandlerEntity entity = new GlobalValueHandlerEntity("Global" + i,
                                                                               LocalDate.now().plusDays(i),
                                                                               LocalDateTime.now().plusDays(i),
                                                                               LocalTime.now().plusMinutes(i));
                em.persist(entity);

                if (i == 40)
                {
                    theOneChecked = entity;
                }
            }

            em.getTransaction().commit();
            em.close();
        }

        {
            EntityManager em = emf.createEntityManager();

            List<GlobalValueHandlerEntity> results = em.createQuery("SELECT g FROM GlobalValueHandlerEntity AS g WHERE g.localDate = :date", GlobalValueHandlerEntity.class)
                                                    .setParameter("date", theOneChecked.getLocalDate())
                                                    .getResultList();
            Assert.assertEquals(results.size(), 1);

            GlobalValueHandlerEntity acutal = results.get(0);
            Assert.assertEquals(acutal.getId(), theOneChecked.getId());
            Assert.assertEquals(acutal.getLocalDate(), theOneChecked.getLocalDate());
            Assert.assertEquals(acutal.getLocalDateTime(), theOneChecked.getLocalDateTime());
            Assert.assertEquals(acutal.getLocalTime(), theOneChecked.getLocalTime());
            Assert.assertEquals(acutal.getName(), theOneChecked.getName());
        }
    }

}
