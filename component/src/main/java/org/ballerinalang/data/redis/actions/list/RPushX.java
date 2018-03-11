/*
 * Copyright (c) 2018, WSO2 Inc. (http:www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http:www.apache.orglicensesLICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.ballerinalang.data.redis.actions.list;

import org.ballerinalang.bre.Context;
import org.ballerinalang.connector.api.ConnectorFuture;
import org.ballerinalang.data.redis.Constants;
import org.ballerinalang.data.redis.RedisDataSource;
import org.ballerinalang.data.redis.actions.AbstractRedisAction;
import org.ballerinalang.model.types.TypeKind;
import org.ballerinalang.model.values.BConnector;
import org.ballerinalang.model.values.BInteger;
import org.ballerinalang.model.values.BStringArray;
import org.ballerinalang.natives.annotations.Argument;
import org.ballerinalang.natives.annotations.BallerinaAction;
import org.ballerinalang.util.exceptions.BallerinaException;

/**
 * {@code rPushX} Maps with "RPUSHX" operation of Redis.
 *
 * @since 0.5.0
 */
@BallerinaAction(packageName = "ballerina.data.redis",
                 actionName = "rPushX",
                 connectorName = Constants.CONNECTOR_NAME,
                 args = {
                         @Argument(name = "c",
                                   type = TypeKind.CONNECTOR)
                 })
public class RPushX extends AbstractRedisAction {

    @Override
    public ConnectorFuture execute(Context context) {
        BConnector bConnector = (BConnector) getRefArgument(context, 0);
        RedisDataSource<String, String> redisDataSource = getDataSource(bConnector);

        String key = getStringArgument(context, 0);
        BStringArray values = (BStringArray) getRefArgument(context, 1);
        if (values == null) {
            throw new BallerinaException("Value array " + MUST_NOT_BE_NULL);
        }
        String[] valuesArray = createArrayFromBStringArray(values);
        BInteger result = rPushX(key, redisDataSource, valuesArray);
        context.getControlStack().getCurrentFrame().returnValues[0] = result;
        return getConnectorFuture();
    }
}
