/*
 * Copyright 2013 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zxing.pdf417.decoder;

import android.util.SparseIntArray;

import zxing.pdf417.PDF417Common;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Guenther Grau
 */
final class BarcodeValue {
	private final SparseIntArray values = new SparseIntArray();

	/**
	 * Add an occurrence of a value
	 * 
	 * @param value
	 */
	void setValue(int value) {
		Integer confidence = values.get(value);
		if (confidence == null) {
			confidence = 0;
		}
		confidence++;
		values.put(value, confidence);
	}

	/**
	 * Determines the maximum occurrence of a set value and returns all values
	 * which were set with this occurrence.
	 * 
	 * @return an array of int, containing the values with the highest
	 *         occurrence, or null, if no value was set
	 */
	int[] getValue() {
		int maxConfidence = -1;
		Collection<Integer> result = new ArrayList<Integer>();
		int key = 0;
		for (int i = 0; i < values.size(); i++) {
			key = values.keyAt(i);
			Integer value = values.get(key);
			if (value > maxConfidence) {
				maxConfidence = value;
				result.clear();
				result.add(key);
			} else if (value == maxConfidence) {
				result.add(key);
			}
		}
		return PDF417Common.toIntArray(result);
	}

	public Integer getConfidence(int value) {
		return values.get(value);
	}

}
