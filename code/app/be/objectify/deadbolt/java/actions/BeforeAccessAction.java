/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.objectify.deadbolt.java.actions;

import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.utils.PluginUtils;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import java.util.concurrent.TimeUnit;

/**
 * Invokes beforeAuthCheck on the global or a specific {@link be.objectify.deadbolt.java.DeadboltHandler}.
 *
 * @author Steve Chaloner (steve@objectify.be)
 */
public class BeforeAccessAction extends AbstractDeadboltAction<BeforeAccess>
{
    /**
     * {@inheritDoc}
     */
    @Override
    public F.Promise<Result> execute(final Http.Context ctx) throws Throwable
    {
        final F.Promise<Result> result;
        if (isActionAuthorised(ctx) && !configuration.alwaysExecute())
        {
            result = delegate.call(ctx);
        }
        else
        {
            final DeadboltHandler deadboltHandler = getDeadboltHandler(configuration.handlerKey(),
                                                                 configuration.value());
            result = preAuth(true,
                             ctx,
                             deadboltHandler)
                    .flatMap(new F.Function<Result, F.Promise<Result>>()
                    {
                        @Override
                        public F.Promise<Result> apply(final Result preAuthResult) throws Throwable
                        {
                            final F.Promise<Result> innerResult;
                            if (preAuthResult != null)
                            {
                                innerResult = F.Promise.pure(preAuthResult);
                            }
                            else
                            {
                                innerResult = delegate.call(ctx);
                            }
                            return innerResult;
                        }
                    });
        }
        return result;
    }
}
