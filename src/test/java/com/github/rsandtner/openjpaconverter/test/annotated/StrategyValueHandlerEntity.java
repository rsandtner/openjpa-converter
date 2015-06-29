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
package com.github.rsandtner.openjpaconverter.test.annotated;

import org.apache.openjpa.persistence.jdbc.Strategy;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * This entity uses the {@link Strategy}-Annotation to specifiy the mapping
 */
@Entity
public class StrategyValueHandlerEntity
{

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private Integer optlock;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    @Strategy(value = "com.github.rsandtner.openjpaconverter.JodaTimeValueHandler")
    private LocalDate localDate;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @Strategy(value = "com.github.rsandtner.openjpaconverter.JodaTimeValueHandler")
    private LocalDateTime localDateTime;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIME)
    @Strategy(value = "com.github.rsandtner.openjpaconverter.JodaTimeValueHandler")
    private LocalTime localTime;


    public StrategyValueHandlerEntity(String name, LocalDate localDate, LocalDateTime localDateTime, LocalTime localTime)
    {
        this.name = name;
        this.localDate = localDate;
        this.localDateTime = localDateTime;
        this.localTime = localTime;
    }


    public Long getId()
    {
        return id;
    }

    public Integer getOptlock()
    {
        return optlock;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public LocalDate getLocalDate()
    {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate)
    {
        this.localDate = localDate;
    }

    public LocalDateTime getLocalDateTime()
    {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime)
    {
        this.localDateTime = localDateTime;
    }

    public LocalTime getLocalTime()
    {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime)
    {
        this.localTime = localTime;
    }
}
